package com.ngochuy.ecommerce.feature.addressbook

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.AddressType
import com.ngochuy.ecommerce.data.ShoppingAddress
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.addressbook.adapter.AddressAdapter
import com.ngochuy.ecommerce.feature.invocie.InvoiceDetailActivity
import com.ngochuy.ecommerce.viewmodel.SharedViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_address_book.*
import kotlinx.android.synthetic.main.fragment_category.*

class AddressBookFragment : Fragment(){

    //private lateinit var sharedModel: SharedViewModel

    private val sharedModel: SharedViewModel by activityViewModels()

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }

    private val adapter by lazy {
        AddressAdapter { address, type -> eventsAddress(address, type)}
    }

    private fun eventsAddress(address: ShoppingAddress, type: AddressType) {
        when (type) {
            AddressType.DEL -> delItemAddress(address)
            AddressType.CLICK -> onClickItemAddress(address)
        }
    }

    private fun delItemAddress(address: ShoppingAddress){
        userViewModel.deleteAddress(address.addressId)
        userViewModel.getAddress(requireContext().getIntPref(USER_ID))
    }

    private fun onClickItemAddress(address: ShoppingAddress) {
        sharedModel.select(address)
        requireActivity().onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.getAddress(requireContext().getIntPref(USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        addEvents()
        bindViewModel()

    }

    override fun onResume() {
        super.onResume()
        userViewModel.getAddress(requireContext().getIntPref(USER_ID))
    }

    private fun initAdapter() {
        rvAddress.adapter = adapter
    }

    private fun addEvents() {
        swlRefresh.setOnRefreshListener {
            userViewModel.getAddress(requireContext().getIntPref(USER_ID))
            swlRefresh.isRefreshing = false
        }
        fabCreateAddress.setOnClickListener {
            val intent = Intent(requireContext(), AddressDetailActivity::class.java)
            startActivity(intent)
        }

    }

    fun bindViewModel() {
        userViewModel.addressUser.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

    }

}