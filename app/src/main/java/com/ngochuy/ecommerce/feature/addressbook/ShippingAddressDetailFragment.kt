package com.ngochuy.ecommerce.feature.addressbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.AddressRequest
import com.ngochuy.ecommerce.databinding.FragmentDetailShippingAddressBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.authentication.LoginFragment
import com.ngochuy.ecommerce.viewmodel.UserViewModel

class ShippingAddressDetailFragment : Fragment(){

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }
    private lateinit var binding: FragmentDetailShippingAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailShippingAddressBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        binding.fragment = this
    }

    private fun bindViewModel(){
        userViewModel.resultAddressAdd.observe(viewLifecycleOwner, {
            when (it.isStatus) {
                1 -> {
                    Toast.makeText(
                        requireContext(),
                        "Thêm địa chỉ thành công",
                        Toast.LENGTH_LONG
                    ).show()
                    requireActivity().onBackPressed()
                }
                0 -> {
                    Toast.makeText(
                        requireContext(),
                        "error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    fun onAddAddress() {
        var check = true
        val name = binding.edtName.textTrim()
        val phone = binding.edtPhone.textTrim()
        val provice = binding.edtPProvince.textTrim()
        val district = binding.edtDistrict.textTrim()
        val ward = binding.edtWards.textTrim()
        val street = binding.edtStreet.textTrim()
        val default= if (binding.sbDefaultAddress.isChecked) 1 else  0
        val active = 1

        if (name.isEmpty()) {
            binding.edtName.error = getString(R.string.error_input_name_not_entered)
            check = false
        }
        if (phone.isEmpty()) {
            binding.edtPhone.error = getString(R.string.error_input_phone_not_entered)
            check = false
        } else if (!isValidPhoneNumber(phone)) {
            binding.edtPhone.error = getString(R.string.error_input_phone_not_correct)
            check = false
        }
        if (provice.isEmpty()) {
            binding.edtPProvince.error = getString(R.string.error_is_empty)
            check = false
        }
        if (district.isEmpty()) {
            binding.edtDistrict.error = getString(R.string.error_is_empty)
            check = false
        }
        if (ward.isEmpty()) {
            binding.edtWards.error = getString(R.string.error_is_empty)
            check = false
        }
        if (street.isEmpty()) {
            binding.edtStreet.error = getString(R.string.error_is_empty)
            check = false
        }

        if (check) {
            val shipAddress = AddressRequest(requireContext().getIntPref(USER_ID),active,district,default,name,phone,provice,street,ward)
            userViewModel.addAddress(shipAddress)
        }
    }
}