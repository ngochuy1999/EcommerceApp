package com.ngochuy.ecommerce.feature.product

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityProductInCategoryBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.feature.product.adapter.ProductAdapter
import com.ngochuy.ecommerce.roomdb.CartDatabase
import com.ngochuy.ecommerce.roomdb.ProductEntity
import com.ngochuy.ecommerce.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.activity_product_in_category.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import kotlin.coroutines.CoroutineContext

class ProductsOfCategoryActivity : AppCompatActivity(),CoroutineScope {

    private var categoryID: String? = null

    private lateinit var binding: ActivityProductInCategoryBinding
    private val productAdapter by lazy {
        ProductAdapter { productID -> showProductDetail(productID) }
    }

    private var cartDB: CartDatabase?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    private fun showProductDetail(id: Int) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra(PRODUCT_ID, id)
        startActivity(intent)
    }

    private val productViewModel: ProductsViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideProductsViewModelFactory()
        )[ProductsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_in_category)

        categoryID = intent.getStringExtra(CATEGORY_ID)
        if (categoryID != null) {
            productViewModel.getProductCategory(categoryID!!)
            initViews()
            bindViewModel()
            addEvents()

            // Set cart count, check user is login yet? check by get userID from Shared pref
            val userId = getIntPref(USER_ID)
            binding.cartCount = 0
            if (userId != -1){
                mJob = Job()
                cartDB = CartDatabase.getDatabase(this)
                launch {
                    val products: List<ProductEntity>? = cartDB?.productDao()?.getAllProduct()
                    binding.cartCount= products?.size
                }
            }

        } else {
            Toast
                .makeText(applicationContext, "Can't load info of this product!", Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }

    private fun initViews() {
        rvProducts.adapter = productAdapter
        rvProducts.setHasFixedSize(true)
        rvProducts.setItemViewCacheSize(20)
    }

    private fun addEvents() {
        btnBackProducts.setOnClickListener { finish() }
        swRefreshProducts.setOnRefreshListener { productViewModel.getProductCategory(categoryID!!) }
        cartProducts.setOnClickListener {
            if (getIntPref(USER_ID) != -1) startActivity<CartActivity>()

        }
        btnContinueShopping.setOnClickListener { startActivity<MainActivity>(); finish() }
        edtSearchProducts.addTextChangedListener(object : TextWatcher {
              override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                productAdapter.search(s.toString(), {
                    productAdapter.removeAllData()
                }, {
                    productAdapter.addDataSearch(it)
                })
            }
        })
    }

    private fun bindViewModel() {
        productViewModel.listProductCategory.observe(this, Observer {
            if (it != null) {
                productsEmpty.gone()
                productAdapter.setProductList(it)
            } else productsEmpty.visible()
        })

        productViewModel.networkStateProCategory.observe(this, Observer {
            progressProducts.visibility =
                if (it.status == Status.RUNNING) View.VISIBLE else View.GONE
        })

    }
}
