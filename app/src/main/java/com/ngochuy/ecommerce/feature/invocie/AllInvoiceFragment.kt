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
import com.ngochuy.ecommerce.feature.invocie.adapter.AllInvoiceAdapter
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.feature.invocie.adapter.ItemConfirmAdapter
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_all_invoice.*
import kotlinx.android.synthetic.main.fragment_item_confirm.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A fragment representing a list of Items.
 */
class AllInvoiceFragment :Fragment(){

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            Injection.provideOrderViewModelFactory()
        )[OrderViewModel::class.java]
    }


    private val invoiceAdapter: AllInvoiceAdapter by lazy {
        AllInvoiceAdapter { id -> showProduct(id) }
    }

    private fun showProduct(id: Int) {
        val intent = Intent(requireContext(), InvoiceDetailActivity::class.java)
        intent.putExtra(INVOICE_ID, id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.getAllOrderItem(requireContext().getIntPref(USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_invoice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initViews()
        setEvent()
    }

    private fun setEvent() {
        btn_continue_shopping_all.setOnClickListener {
            startActivity<MainActivity>()
            requireActivity().finish()
        }
    }
    private fun initViews() {
        listAll.adapter = invoiceAdapter
        listAll.setHasFixedSize(true)
        listAll.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        orderViewModel.allOrderItem.observe(viewLifecycleOwner) {
            if (it?.size != 0 ) {
                invoiceAdapter.setProductList(it ?: arrayListOf())
                ll_all_empty.gone()
            }else ll_all_empty.visible()
        }

        orderViewModel.networkAllOrderItem.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.RUNNING -> progressAll.visible()
                Status.SUCCESS -> {
                    progressAll.gone()
                }
                Status.FAILED -> {
                    progressAll.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }


    }


}