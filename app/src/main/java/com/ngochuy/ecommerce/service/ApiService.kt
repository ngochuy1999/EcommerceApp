package com.ngochuy.ecommerce.service

import com.ngochuy.ecommerce.data.*
import retrofit2.http.Query

class ApiService(private val apiApi: ApiManager) {

    fun getListProductSale(
            onPrepared: () -> Unit,
            onSuccess: (ArrayList<Product>?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getListProductSale()
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getListSlide(
            onPrepared: () -> Unit,
            onSuccess: (ArrayList<Slide>?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getListSlider()
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getAllListProductSale(
            onPrepared: () -> Unit,
            onSuccess: (ArrayList<Product>?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getListProductSale()
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getAllPros(
            onPrepared: () -> Unit,
            onSuccess: (ArrayList<Product>?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getAllProducts()
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getListProductCategory(
            cateId: Int,
            onPrepared: () -> Unit,
            onSuccess: (ArrayList<Product>?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getListProductOfCategory(cateId)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getProductDetailByID(
            productID: Int,
            onPrepared: () -> Unit,
            onSuccess: (Product?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getProductDetailByID(productID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun addCart(
            userID: Int,
            productID: Int,
            quantity: Int,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.plusCart(userID, productID, quantity)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getListCategory(
            onPrepared: () -> Unit,
            onSuccess: (ArrayList<String>?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getListCategory()
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getCartCount(
            userID: Int,
            onPrepared: () -> Unit,
            onSuccess: (Int?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getCartCount(userID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun minusCart(
            userID: Int,
            productID: Int,
            onPrepared: () -> Unit,
            onSuccess: (Boolean?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.minusCart(userID, productID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun plusCart(
            userID: Int,
            productID: Int,
            quantity: Int,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.plusCart(userID, productID, quantity)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun delCart(
            userID: Int,
            productID: Int,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.delItemCart(userID, productID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }


    fun login(
            username: String,
            password: String,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.login(username, password)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun forgotPassword(
            email: String,
            onPrepared: () -> Unit,
            onSuccess: (Int?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.forgotPassword(email)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getUserInfoByUserID(
            userId: Int,
            onPrepared: () -> Unit,
            onSuccess: (User?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getUserInfoByUserID(userId)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun signUp(
            email: String,
            name: String,
            password: String,
            phone: String,
            address: String,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.signUp(email, name , password, phone, address)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun changeUserInfo(
            userId: Int,
            email: String,
            name: String,
            phone: String,
            address: String,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.changeInfoAcc(userId, email, name, phone, address)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun changePassword(
            userId: Int,
            oldPass: String,
            newPass: String,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.changePass(userId, oldPass, newPass)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getProductCart(
            userID: Int,
            onPrepared: () -> Unit,
            onSuccess: (ArrayList<Product>?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.getProductsCart(userID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }


    fun addOrder(
            userID: Int,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.addOrder(userID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }


    fun getAllOrderItem(
            orderID : Int,
            onPrepared: () -> Unit,
            onSuccess: (ResultOrder?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request =  apiApi.getAllOrderItem(orderID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }


}