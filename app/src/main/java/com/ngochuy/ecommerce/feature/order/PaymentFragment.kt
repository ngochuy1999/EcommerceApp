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
import com.ngochuy.ecommerce.feature.order.adapter.PaymentRecyclerViewAdapter
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_accomplished_list.*
import kotlinx.android.synthetic.main.fragment_delivering.*
import kotlinx.android.synthetic.main.fragment_item_confirm.*
import kotlinx.android.synthetic.main.fragment_payment.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A fragment representing a list of Items.
 */
class PaymentFragment :Fragment(){

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            Injection.provideOrderViewModelFactory()
        )[OrderViewModel::class.java]
    }


    private val productAdapter: PaymentRecyclerViewAdapter by lazy {
        PaymentRecyclerViewAdapter { id -> showProduct(id) }
    }

    private fun showProduct(id: Int) {
        val intent = Intent(requireContext(), OrderDetailPaymentActivity::class.java)
        intent.putExtra(INVOICE_ID, id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.getPaymentOrderItem(requireContext().getIntPref(USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initViews()
        setEvent()
    }

    private fun setEvent() {
        btn_continue_shopping_pa.setOnClickListener {
            startActivity<MainActivity>()
            requireActivity().finish()
        }
    }

    private fun initViews() {
        listPayment.adapter = productAdapter
        listPayment.setHasFixedSize(true)
        listPayment.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        orderViewModel.paymentOrderItem.observe(viewLifecycleOwner) {
            if (it?.size != 0 ) {
                productAdapter.setProductList(it ?: arrayListOf())
                ll_pa_empty.gone()
            }else ll_pa_empty.visible()
        }

        orderViewModel.networkPaymentOrderItem.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.RUNNING -> progressPa.visible()
                Status.SUCCESS -> {
                    progressPa.gone()
                }
                Status.FAILED -> {
                    progressPa.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }


    }


}