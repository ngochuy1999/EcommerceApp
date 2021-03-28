package com.ngochuy.ecommerce.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.repository.AuthRepository
import com.ngochuy.ecommerce.repository.ProductRepository

class UserViewModel(private val repository: AuthRepository) :ViewModel(){
    var email: String = ""
    var password: String = ""
    var name: String = ""
    var phone: String = ""

    fun isValidate(email: String): Boolean {
        return email.isNotEmpty()
    }
}



class UserViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = UserViewModel(repository) as T
}