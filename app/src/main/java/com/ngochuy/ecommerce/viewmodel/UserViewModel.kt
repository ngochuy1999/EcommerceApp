package com.ngochuy.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.User
import com.ngochuy.ecommerce.repository.AuthRepository

class UserViewModel(private val repository: AuthRepository) : ViewModel() {
    var address: String = ""
    var email: String = ""
    var password: String = ""
    var name: String = ""
    var userName: String = ""
    var phone: String = ""

    /*---------------- Check text empty set button enable ----------------*/
    fun isValidate(email: String): Boolean {
        return email.isNotEmpty()
    }

    fun isValidateLogin(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    fun isValidateSignUp(): Boolean =
        userName.isNotEmpty() && name.isNotEmpty() && address.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()

    /*---------------Login---------------*/
    private val requestLogin = MutableLiveData<Result<Int>>()

    val networkStateLogin = Transformations.switchMap(requestLogin) {
        it.networkState
    }

    fun login(email: String, password: String) {
        requestLogin.value = repository.login(email, password)
    }

    /*--------------- Sign Up ---------------*/
    private val requestSignUp = MutableLiveData<Result<Int>>()

    val networkStateSignUp = Transformations.switchMap(requestSignUp) {
        it.networkState
    }

    fun signUp(user: User) {
        requestSignUp.value = repository.signUp(user)
    }

    /*--------------- Forgot password---------------*/
    private val requestForgotPassword = MutableLiveData<Result<Int>>()

    val networkStateForgotPassword = Transformations.switchMap(requestForgotPassword) {
        it.networkState
    }

    fun forgotPassword(email: String) {
        requestForgotPassword.value = repository.forgotPassword(email)
    }
}


class UserViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = UserViewModel(repository) as T
}