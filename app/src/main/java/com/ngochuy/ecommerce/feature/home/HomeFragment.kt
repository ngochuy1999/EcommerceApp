package com.ngochuy.ecommerce.feature.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Slide
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.PRODUCT_ID
import com.ngochuy.ecommerce.ext.gone
import com.ngochuy.ecommerce.ext.visible
import com.ngochuy.ecommerce.feature.home.adapter.ProductSaleAdapter
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.viewmodel.HomeViewModel
import com.ngochuy.ecommerce.widget.GridItemDecoration
import com.ngochuy.ecommerce.widget.SlidingImageAdapter
import java.util.*
import kotlin.math.log

class HomeFragment : Fragment() {

    companion object {
        private var currentPage = 0
        private var NUM_PAGES = 0
    }

    private val productSalAdt: ProductSaleAdapter by lazy {
        ProductSaleAdapter { id -> showProductDetail(id) }
    }

    private val slideAdapter: SlidingImageAdapter by lazy {
        SlidingImageAdapter(requireContext(), arrSlide){
               // productID -> showProductDetail(productID)
        }
    }

    private var arrSlide: ArrayList<Slide> = arrayListOf()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(
            this,
            Injection.provideHomeViewModelFactory()
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        addEvents()
        bindViewModel()
    }

    private fun playSlide() {
        pagerHome.adapter= SlidingImageAdapter(requireContext(), arrSlide){
                productID -> showProductDetail(productID)
        }
        indicator.setViewPager(pagerHome)

        val density = resources.displayMetrics.density
        indicator.radius = 3 * density

        NUM_PAGES = arrSlide.size

        // Auto start of viewpager
        val handler = Handler()
        val update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            pagerHome?.setCurrentItem(currentPage++, true)
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000, 3000)
    }

    private fun bindViewModel() {

        homeViewModel.listProductSale.observe(viewLifecycleOwner, Observer {
            productSalAdt.setProductList(it)
        })

        homeViewModel.networkStateProSale.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.RUNNING -> progressHome.visible()
                Status.SUCCESS -> progressHome.gone()
                Status.FAILED -> {
                    progressHome.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        })
        homeViewModel.networkStateSlide.observe(viewLifecycleOwner, Observer {
            progressHome.visibility = if (it.status == Status.RUNNING) View.VISIBLE else View.GONE
        })
        homeViewModel.listSlides.observe(viewLifecycleOwner, Observer {
            arrSlide = it
            slideAdapter.notifyDataSetChanged()
            playSlide()
        })
    }

    private fun initViews() {
        rv_product_home.adapter = productSalAdt
        rv_product_home.setHasFixedSize(true)
        rv_product_home.setItemViewCacheSize(20)
        rv_product_home.layoutManager = GridLayoutManager(requireContext(), 2)
        rv_product_home.addItemDecoration(GridItemDecoration(10, 2))

//        pagerHome.adapter= slideAdapter
//        indicator.setViewPager(pagerHome)
//
//        val density = resources.displayMetrics.density
//        indicator.radius = 5 * density
    }

    private fun addEvents() {
        swRefreshHome.setOnRefreshListener { homeViewModel.refresh() }
    }

    private fun showProductDetail(id: Int?) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra(PRODUCT_ID, id)
        requireActivity().startActivity(intent)
    }
}
