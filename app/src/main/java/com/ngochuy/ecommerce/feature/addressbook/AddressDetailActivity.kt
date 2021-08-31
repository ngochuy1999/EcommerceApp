package com.ngochuy.ecommerce.feature.addressbook

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.AddressRequest
import com.ngochuy.ecommerce.databinding.ActivityAddressDetailBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.viewmodel.UserViewModel

class AddressDetailActivity : AppCompatActivity() {


    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }

    private lateinit var binding: ActivityAddressDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_detail)
        bindViewModel()

    }

    private fun bindViewModel() {
        userViewModel.resultAddressAdd.observe(this, {
            when (it.isStatus) {
                1 -> {
                    Toast.makeText(
                        this,
                        "Thêm địa chỉ thành công",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                0 -> {
                    Toast.makeText(
                        this,
                        "error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    fun onAddAddress(view: View) {
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
            val shipAddress = AddressRequest(getIntPref(USER_ID),active,district,default,name,phone,provice,street,ward)
            userViewModel.addAddress(shipAddress)
        }
    }
}