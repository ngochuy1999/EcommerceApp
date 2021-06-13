package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.User
import com.ngochuy.ecommerce.databinding.ActivityTouchidBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_touchid.*
import org.jetbrains.anko.startActivity

class TouchIDActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }
    val responseUser = MutableLiveData<User>()
    private lateinit var binding: ActivityTouchidBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_touchid)
        userViewModel.getInfoUser((USER_ID) ?: 0)
        bindViewModel()
        swTouch.isChecked = CHECK_FINGER
        swTouch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                CHECK_FINGER = true
                USER_TOUCHID = responseUser.value?.email.toString()
                PASS_TOUCHID = responseUser.value?.password.toString()
                Toast.makeText(this , USER_TOUCHID, Toast.LENGTH_SHORT).show()

            } else {
                CHECK_FINGER = false
                USER_TOUCHID = ""
                PASS_TOUCHID = ""
            }
        }

    }

    private fun bindViewModel() {
        userViewModel.userInfo.observe(this) {
            responseUser.value = it
        }
    }

    fun onBack(view: View) {
        finish()
    }

}