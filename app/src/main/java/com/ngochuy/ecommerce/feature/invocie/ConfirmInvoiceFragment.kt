package com.ngochuy.ecommerce.feature.invocie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.ngochuy.ecommerce.feature.invocie.adapter.ItemConfirmAdapter
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_item_confirm.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A fragment representing a list of Items.
 */
class ConfirmInvoiceFragment :Fragment(){

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            Injection.provideOrderViewModelFactory()
        )[OrderViewModel::class.java]
    }


    private val invoiceAdapter: ItemConfirmAdapter by lazy {
        ItemConfirmAdapter { id -> showProduct(id) }
    }

    private fun showProduct(id: Int) {
        val intent = Intent(requireContext(), InvoiceDetailActivity::class.java)
        intent.putExtra(INVOICE_ID, id)
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
        setEvent()
    }

    private fun setEvent() {
        btn_continue_shopping_co.setOnClickListener {
            startActivity<MainActivity>()
            requireActivity().finish()
        }
    }
    private fun initViews() {
        listConfirm.adapter = invoiceAdapter
        listConfirm.setHasFixedSize(true)
        listConfirm.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        orderViewModel.confirmOrderItem.observe(viewLifecycleOwner) {
            if (it?.size != 0 ) {
                invoiceAdapter.setProductList(it ?: arrayListOf())
                ll_co_empty.gone()
            }else ll_co_empty.visible()
        }

        orderViewModel.networkConfirmOrderItem.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.RUNNING -> progressCo.visible()
                Status.SUCCESS -> {
                    progressCo.gone()
                }
                Status.FAILED -> {
                    progressCo.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }


    }


}