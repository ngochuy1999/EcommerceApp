package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityEditProfileBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import org.jetbrains.anko.startActivity

class EditProfileActivity : AppCompatActivity() {


    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        userViewModel.getInfoUser(getIntPref(USER_ID) ?: 0)
        bindViewModel()
    }

    private fun bindViewModel() {
        userViewModel.userInfo.observe(this) {
            binding.user = it
        }

        userViewModel.statusChangeInfo.observe(this) {
            when (it.isStatus) {
                0 -> {
                    Toast.makeText(this, getString(R.string.err_edit_user), Toast.LENGTH_LONG).show()
                }
                1 -> {
                    Toast.makeText(this, getString(R.string.edit_user), Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }

        userViewModel.networkUserInfo.observe(this)
        {
            when (it.status) {
                Status.RUNNING -> binding.progressEditProfile.visible()
                Status.SUCCESS -> {
                    binding.progressEditProfile.gone()
                }
                Status.FAILED -> {
                    binding.progressEditProfile.gone()
                    Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }

        userViewModel.networkChangeInfo.observe(this)
        {
            when (it.status) {
                Status.RUNNING -> binding.progressEditProfile.visible()
                Status.SUCCESS -> {
                    binding.progressEditProfile.gone()
                }
                Status.FAILED -> {
                    binding.progressEditProfile.gone()
                    Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun onBack(view: View) {
        finish()
    }


    fun onClickChangeInfo(view: View) {
        var check = true
        val name = binding.edtNameEdit.textTrim()
        val phone = binding.edtPhoneEdit.textTrim()
        val address = binding.edtAddress.textTrim()

        if (name.isEmpty()) {
            binding.edtNameEdit.error = getString(R.string.error_input_name_not_entered)
            check = false
        }
        if (phone.isEmpty()) {
            binding.edtPhoneEdit.error = getString(R.string.error_input_phone_not_entered)
            check = false
        }
        if (address.isEmpty()) {
            binding.edtAddress.error = getString(R.string.error_input_email_not_entered)
            check = false
        }
        if (check) {
            userViewModel.changeInfo(
                    userViewModel.userInfo.value?.userId ?: 0,
                    name,
                    phone,
                    address
            )
        }
    }
}
