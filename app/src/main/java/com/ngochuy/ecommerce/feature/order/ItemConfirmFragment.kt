package com.ngochuy.ecommerce.feature.order

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.PRODUCT_ID
import com.ngochuy.ecommerce.ext.USER_ID
import com.ngochuy.ecommerce.ext.getIntPref
import com.ngochuy.ecommerce.feature.order.adapter.AccomplishedFragmentAdapter
import com.ngochuy.ecommerce.feature.order.adapter.ItemConfirmRecyclerViewAdapter
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_accomplished_list.*
import kotlinx.android.synthetic.main.fragment_item_confirm.*

/**
 * A fragment representing a list of Items.
 */
class ItemConfirmFragment :Fragment(){

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            Injection.provideOrderViewModelFactory()
        )[OrderViewModel::class.java]
    }


    private val productAdapter: ItemConfirmRecyclerViewAdapter by lazy {
        ItemConfirmRecyclerViewAdapter { id -> showProduct(id) }
    }

    private fun showProduct(id: Int) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra(PRODUCT_ID, id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.getConfirmOrderItem(requireContext().getIntPref(USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initViews()
    }

    private fun initViews() {
        listConfirm.adapter = productAdapter
        listConfirm.setHasFixedSize(true)
        listConfirm.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        orderViewModel.confirmOrderItem.observe(viewLifecycleOwner) {
            productAdapter.setProductList(it.result ?: arrayListOf())
        }

//        orderViewModel.networkOrderItem.observe(viewLifecycleOwner) {
//            when (it.status) {
//                Status.RUNNING -> progressOrderDetail.visible()
//                Status.SUCCESS -> {
//                    progressOrderDetail.gone()
//                }
//                Status.FAILED -> {
//                    progressOrderDetail.gone()
//                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
//                }
//            }
//        }

    }


}