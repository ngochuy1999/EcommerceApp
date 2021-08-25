package com.ngochuy.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.ResultApi
import com.ngochuy.ecommerce.repository.CartRepository

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    private val requestCartCount = MutableLiveData<Result<Int>>()
    private val requestProductsCart = MutableLiveData<Result<ArrayList<Product>>>()
    private val requestPlusCart = MutableLiveData<Result<ResultApi>>()
    private val requestMinusCart = MutableLiveData<Result<Boolean>>()
    private val requestDelCart = MutableLiveData<Result<ResultApi>>()
    private val requestOTP = MutableLiveData<Result<String>>()
    val name = MutableLiveData<String>().apply { value = "" }
    val phone = MutableLiveData<String>().apply { value = "" }
    val address = MutableLiveData<String>().apply { value = "" }
    val email = MutableLiveData<String>().apply { value = "" }

//    val cartCount = Transformations.switchMap(requestCartCount) {
//        it.data
//    }
//
//    fun getCartCount(userID: Int) {
//        requestCartCount.value = repository.getCartCount(userID)
//    }
//
//    val productsCart = Transformations.switchMap(requestProductsCart) {
//        it.data
//    }
//    val networkProductsCart = Transformations.switchMap(requestProductsCart) {
//        it.networkState
//    }
//
//    fun getProductsCart(userID: Int) {
//        requestProductsCart.value = repository.getProductCart(userID)
//    }
//
//    val networkPlusCart = Transformations.switchMap(requestPlusCart) {
//        it.networkState
//    }
//
//    fun plusCart(userID: Int, productId: Int, quantity: Int) {
//        requestPlusCart.value = repository.plusCart(userID, productId, quantity)
//    }
//
//    val networkMinusCart = Transformations.switchMap(requestMinusCart) {
//        it.networkState
//    }
//
//    fun minusCart(userID: Int, productId: Int) {
//        requestMinusCart.value = repository.minusCart(userID, productId)
//    }
//
//    val networkDelCart = Transformations.switchMap(requestDelCart) {
//        it.networkState
//    }
//
//    val delCart = Transformations.switchMap(requestDelCart) {
//        it.data
//    }
//
//    fun delCartItem(userID: Int, productId: Int) {
//        requestDelCart.value = repository.delCart(userID, productId)
//    }
//    fun getOTP(email: String) {
//        requestOTP.value = repository.getOtp(email)
//    }
//    val codeOTP = Transformations.switchMap(requestOTP) {
//        it.data
//    }
//    val networkCodeOTP = Transformations.switchMap(requestOTP) {
//        it.networkState
//    }

}

class CartViewModelFactory(
    private val repository: CartRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = CartViewModel(repository) as T
}