package com.ngochuy.ecommerce.feature.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Order
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivitySearchBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.feature.product.adapter.ProductAdapter
import com.ngochuy.ecommerce.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.activity_order_detail.btnBack
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private val productAdapter by lazy {
        ProductAdapter { productID -> showProductDetail(productID) }
    }

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
    private lateinit var binding: ActivitySearchBinding
    private var order: Order? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initViews()
        bindingViewModel()
        setEvents()
        productViewModel.getAllProducts()
    }

    private fun setEvents() {
        edtSearchMain.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                eventSearch(s.toString())
            }
        })
        btnBack.setOnClickListener { finish() }
    }

    private fun initViews() {
        rv_search.adapter = productAdapter
        rv_search.setHasFixedSize(true)
        rv_search.setItemViewCacheSize(20)
    }

    private fun bindingViewModel() {
        productViewModel.listProducts.observe(this, Observer {
            productAdapter.setProductList(it)
        })

        productViewModel.networkStateAllPros.observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> progressSearch.visible()
                Status.SUCCESS -> progressSearch.gone()
                Status.FAILED -> {
                    progressSearch.gone()
                    Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    fun eventSearch(strSearch: String) {
//        if(strSearch == ""){
//            productAdapter.setProductList(productViewModel.listProducts.value?: arrayListOf())
//        }
        productAdapter.search(strSearch, onNothingFound={
            productAdapter.removeAllData()
            ll_search_empty?.visible()
        }, onSearchResult={
            productAdapter.addDataSearch(it)
            ll_search_empty?.gone()
        })
    }
}