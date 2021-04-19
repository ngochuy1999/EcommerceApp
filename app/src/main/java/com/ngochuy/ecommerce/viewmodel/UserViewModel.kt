package com.ngochuy.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.ResultApi
import com.ngochuy.ecommerce.data.User
import com.ngochuy.ecommerce.repository.AuthRepository

class UserViewModel(private val repository: AuthRepository) : ViewModel() {
    var address = MutableLiveData<String>().apply { value = "" }
    var password = MutableLiveData<String>().apply { value = "" }
    var name = MutableLiveData<String>().apply { value = "" }
    var username = MutableLiveData<String>().apply { value = "" }
    var phone = MutableLiveData<String>().apply { value = "" }
    var email = MutableLiveData<String>().apply { value = "" }

    /*---------------- Check text empty set button enable ----------------*/
    fun isValidate(username: String): Boolean {
        return username.isNotEmpty()
    }

    fun isValidateLogin(): Boolean {
        return !username.value.equals("") && !password.value.equals("")
    }

    fun isValidateSignUp(): Boolean =
            !username.value.equals("") && !name.value.equals("") && !address.value.equals("")
                    && !phone.value.equals("") && !email.value.equals("") && !password.value.equals("")

    /*---------------Login---------------*/
    private val requestLogin = MutableLiveData<Result<ResultApi>>()

    val networkStateLogin = Transformations.switchMap(requestLogin) {
        it.networkState
    }

    val resultLogin = Transformations.switchMap(requestLogin) {
        it.data
    }

    fun login(username : String, password: String) {
        requestLogin.value = repository.login(username, password)
    }


    /*--------------- Sign Up ---------------*/
//    private val requestSignUp = MutableLiveData<Result<Int>>()
//
//    val networkStateSignUp = Transformations.switchMap(requestSignUp) {
//        it.networkState
//    }
//
//    fun signUp(user: User) {
//        requestSignUp.value = repository.signUp(user)
//    }

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