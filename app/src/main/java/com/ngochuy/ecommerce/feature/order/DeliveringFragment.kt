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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.feature.order.adapter.AccomplishedFragmentAdapter
import com.ngochuy.ecommerce.feature.order.adapter.DeliveringRecyclerViewAdapter
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_accomplished_list.*
import kotlinx.android.synthetic.main.fragment_delivering.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A fragment representing a list of Items.
 */
class DeliveringFragment :Fragment(){

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            Injection.provideOrderViewModelFactory()
        )[OrderViewModel::class.java]
    }


    private val productAdapter: DeliveringRecyclerViewAdapter by lazy {
        DeliveringRecyclerViewAdapter { id -> showProduct(id) }
    }

    private fun showProduct(id: Int) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra(PRODUCT_ID, id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.getDeliverOrderItem(requireContext().getIntPref(USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivering, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initViews()
        setEvent()
    }

    private fun setEvent() {
        btn_continue_shopping_de.setOnClickListener {
            startActivity<MainActivity>()
            requireActivity().finish()
        }
    }

    private fun initViews() {
        listDelivering.adapter = productAdapter
        listDelivering.setHasFixedSize(true)
        listDelivering.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        orderViewModel.deliverOrderItem.observe(viewLifecycleOwner) {
            if (it.result?.size != 0 ) {
                productAdapter.setProductList(it.result ?: arrayListOf())
                ll_deliver_empty.gone()
            }else ll_deliver_empty.visible()
        }

        orderViewModel.networkDeliverOrderItem.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.RUNNING -> progressDeliver.visible()
                Status.SUCCESS -> {
                    progressDeliver.gone()
                }
                Status.FAILED -> {
                    progressDeliver.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }

    }


}