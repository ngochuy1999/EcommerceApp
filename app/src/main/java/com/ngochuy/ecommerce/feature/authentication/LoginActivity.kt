package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.replaceFragment
import com.ngochuy.ecommerce.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userViewModel = ViewModelProvider(this, Injection.provideAuthViewModelFactory())[UserViewModel::class.java]
        replaceFragment(fragment = SignInUpFragment())
    }

    fun onBackLogin(view: View) { finish()}
}
