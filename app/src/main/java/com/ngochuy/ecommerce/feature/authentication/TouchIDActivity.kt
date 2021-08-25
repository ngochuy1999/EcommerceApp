package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.User
import com.ngochuy.ecommerce.databinding.ActivityTouchidBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_touchid.*

class TouchIDActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }
    var responseUser = MutableLiveData<User>()
    private lateinit var binding: ActivityTouchidBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_touchid)
        userViewModel.getInfoUser(getIntPref(USER_ID) ?: 0)
        bindViewModel()
        swTouch.isChecked = getBooleanPref(CHECK_FINGER)
        swTouch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setBooleanPref(CHECK_FINGER,true)
                setIntPref(USERID_TOUCHID,getIntPref(USER_ID))
            } else {
                setBooleanPref(CHECK_FINGER,false)
                removeValueSharePrefs(USERID_TOUCHID)
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