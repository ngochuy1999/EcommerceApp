package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityChangePassBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.cart.OrderSuccessFragment
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_order_success.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

class ChangePassActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }

    private lateinit var binding: ActivityChangePassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_pass)
        userViewModel.getInfoUser(getIntPref(USER_ID) ?: 0)
        bindViewModel()
    }

    private fun bindViewModel() {

        userViewModel.dataChangePass.observe(this) {
            when (it.isStatus) {
                2 -> {
                    Toast.makeText(this, getString(R.string.err_change_pass), Toast.LENGTH_LONG).show()
                }
                1 -> {
                    Toast.makeText(this, getString(R.string.change_pass), Toast.LENGTH_LONG).show()
                    startActivity<MainActivity>()
                    finish()
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
                    Toast.makeText(this,it.msg , Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun onBack(view: View) {
        finish()
    }

    fun onClickChangeInfo(view: View) {
        var check = true
        val pass = binding.edtOldPassword.textTrim()
        val newPass = binding.edtNewPass.textTrim()
        val reNewPass = binding.edtReNewPass.textTrim()

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
            if (check) userViewModel.changePass(getIntPref(USER_ID), pass, newPass)
    }
}