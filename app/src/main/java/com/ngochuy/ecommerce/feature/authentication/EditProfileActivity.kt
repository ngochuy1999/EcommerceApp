package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityEditProfileBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.viewmodel.UserViewModel

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
        userViewModel.getInfoUser((USER_ID) ?: 0)
        bindViewModel()
    }

    private fun bindViewModel() {
        userViewModel.userInfo.observe(this) {
            binding.user = it
        }
        userViewModel.dataChangePass.observe(this) {
            Toast.makeText(this, it.isStatus.toString(), Toast.LENGTH_LONG).show()
        }
        userViewModel.statusChangeInfo.observe(this) {
            Toast.makeText(this, it.isStatus.toString(), Toast.LENGTH_LONG).show()
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

        userViewModel.networkChangePass.observe(this)
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

    fun onCheckChangePassword(view: View) {
        if ((view as CheckBox).isChecked) binding.llEditPassword.visible()
        else binding.llEditPassword.gone()
    }

    fun onClickChangeInfo(view: View) {
        var check = true
        val name = binding.edtNameEdit.textTrim()
        val phone = binding.edtPhoneEdit.textTrim()
        val mail = binding.edtEmailEdit.textTrim()
        val address = binding.edtAddress.textTrim()
        val pass = binding.edtOldPassword.textTrim()
        val newPass = binding.edtNewPass.textTrim()
        val reNewPass = binding.edtReNewPass.textTrim()

        if (name.isEmpty()) {
            binding.edtNameEdit.error = getString(R.string.error_input_name_not_entered)
            check = false
        }
        if (phone.isEmpty()) {
            binding.edtPhoneEdit.error = getString(R.string.error_input_phone_not_entered)
            check = false
        }
        if (mail.isEmpty()) {
            binding.edtEmailEdit.error = getString(R.string.error_input_email_not_entered)
            check = false
        } else if (!isValidEmail(mail)) {
            binding.edtEmailEdit.error = getString(R.string.err_email_not_valid)
            check = false
        }
        if (address.isEmpty()) {
            binding.edtAddress.error = getString(R.string.error_input_email_not_entered)
            check = false
        }
        if (binding.cbChangePw.isChecked) {
            if (pass.isEmpty()) {
                binding.edtOldPassword.error = getString(R.string.error_old_passwords_is_empty)
                check = false
            }
            if (newPass.isEmpty()) {
                binding.edtNewPass.error = getString(R.string.error_new_pw_empty)
                check = false
            }
            if (reNewPass.isEmpty()) {
                binding.edtReNewPass.error = getString(R.string.error_re_new_pw_empty)
                check = false
            }
            if (newPass != reNewPass) {
                binding.edtReNewPass.error = getString(R.string.error_passwords_do_not_match)
                check = false
            }
            if (check) userViewModel.changePass(USER_ID, pass, newPass)
        }

        if (check) {
            userViewModel.changeInfo(
                    userViewModel.userInfo.value?.id ?: 0,
                    name,
                    mail,
                    phone,
                    address
            )
        }
    }
}
