package com.ngochuy.ecommerce.feature.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.PRODUCT_ID
import com.ngochuy.ecommerce.ext.gone
import com.ngochuy.ecommerce.ext.visible
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.feature.product.adapter.ProductAdapter
import com.ngochuy.ecommerce.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_search.*


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private val productAdapter by lazy {
        ProductAdapter { productID -> showProductDetail(productID) }
    }

    private fun showProductDetail(id: Int) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
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
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindingViewModel()
        setEvents()
        productViewModel.getAllProducts()
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

    private fun setEvents() {

    }

    private fun bindingViewModel() {
        productViewModel.listProducts.observe(viewLifecycleOwner, Observer {
            productAdapter.setProductList(it)
        })

        productViewModel.networkStateAllPros.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> progressSearch.visible()
                Status.SUCCESS -> progressSearch.gone()
                Status.FAILED -> {
                    progressSearch.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initViews() {
        rv_search.adapter = productAdapter
        rv_search.setHasFixedSize(true)
        rv_search.setItemViewCacheSize(20)
    }

}
