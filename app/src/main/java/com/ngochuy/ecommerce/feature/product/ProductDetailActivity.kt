package com.ngochuy.ecommerce.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import org.jetbrains.anko.startActivity
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Slide
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityProductDetailBinding
import com.ngochuy.ecommerce.databinding.BottomSheetAddCartBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.product.adapter.ProductDetailAdapter
import com.ngochuy.ecommerce.feature.product.adapter.SlidingImageProductDetailAdapter
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import com.ngochuy.ecommerce.viewmodel.ProductsViewModel

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val productViewModel: ProductsViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideProductsViewModelFactory()
        )[ProductsViewModel::class.java]
    }

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }

    private val slideAdapter: SlidingImageProductDetailAdapter by lazy {
        SlidingImageProductDetailAdapter(this, arrSlide)
    }
    private val productDetailsAdapter by lazy {
        ProductDetailAdapter()
    }

    private var arrSlide: ArrayList<String> = arrayListOf()
    private var productId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        productId = intent.getIntExtra(PRODUCT_ID, -1)
        if (productId != -1 && productId != null) {
            // Set cart count, check user is login yet? check by get userID from Shared pref
            val userId = getIntPref(USER_ID)
            binding.cartCount = 0
            if (userId != -1)
                cartViewModel.getCartCount(userId)

            productViewModel.getProductById(productId!!)
            bindViewModel()
            addEvents()
        } else {
            Toast
                .makeText(applicationContext, "Can't load info of this product!", Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }

    private fun addEvents() {
        binding.cartProductDetail.setOnClickListener {
            // Check user login
            if (getIntPref(USER_ID) != -1) startActivity<CartActivity>()
            else startActivity<LoginActivity>()
        }
        binding.btnBackProductDetail.setOnClickListener {
            finish()
        }
        binding.btnBuy.setOnClickListener {
            addCart()
        }
    }

    private fun addCart() {
        val userId = getIntPref(USER_ID)
        if (userId != -1) {
            productId?.let { productViewModel.addCart(userId, it, 1) }
            showBottomDialogAddCart()
        }
        // If haven't login , intent to login activity
        else startActivity<LoginActivity>()
    }

    private fun showBottomDialogAddCart() {
        val mBottomSheetDialog = RoundedBottomSheetDialog(this)
        val bindingDialog: BottomSheetAddCartBinding = DataBindingUtil
            .inflate(LayoutInflater.from(this), R.layout.bottom_sheet_add_cart, null, false)
        bindingDialog.product = productViewModel.product.value
        mBottomSheetDialog.setContentView(bindingDialog.root)

        // Add events
        bindingDialog.btnCancelDialogAddCart.setOnClickListener() { mBottomSheetDialog.dismiss() }
        bindingDialog.btnViewCart.setOnClickListener() {
            startActivity<CartActivity>()
            mBottomSheetDialog.dismiss()
        }
        mBottomSheetDialog.show()
    }

    private fun bindViewModel() {

        productViewModel.product.observe(this, Observer {
            binding.product = it

            it.details?.let { it1 -> productDetailsAdapter.setListProductDetail(it1) }
            binding.rvProductDetail.adapter = productDetailsAdapter
            binding.rvProductDetail.setHasFixedSize(true)
            binding.rvProductDetail.setItemViewCacheSize(20)

            arrSlide = it.images!!
            slideAdapter.notifyDataSetChanged()
            binding.ivProductDetail.adapter= SlidingImageProductDetailAdapter(this, arrSlide)

            binding.indicatorPd.setViewPager(binding.ivProductDetail)

            val density = resources.displayMetrics.density
            binding.indicatorPd.radius = 3 * density
        })

        productViewModel.networkProduct.observe(this,{
            when(it.status){
                Status.RUNNING -> {
                    binding.shimmerViewContainer.startShimmer()
                }
                Status.FAILED -> {
                    binding.shimmerViewContainer.hideShimmer()
                }
                Status.SUCCESS -> {
                    binding.shimmerViewContainer.hideShimmer()
                }
            }
        })

        productViewModel.resultAddCart.observe(this, Observer {
            when (it.isStatus) {
                0 -> {
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                }
                1 -> {
                    Toast.makeText(this ,"Add cart success", Toast.LENGTH_LONG).show()

                }
            }
        })

        productViewModel.networkStateAddCart.observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> {
                }
                Status.FAILED -> {
                    Toast.makeText(this, "Add cart not success!", Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(this, "Add cart success!", Toast.LENGTH_LONG).show()
                }
            }
        })

        cartViewModel.cartCount.observe(this, Observer {
            binding.cartCount = it ?: 0
        })
    }
}
