package com.ngochuy.ecommerce.service

import com.ngochuy.ecommerce.data.*
import retrofit2.Call

class ApiService(private val apiApi: ApiManager) {

    fun getListProductSale(
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<ProductDetail>?) -> Unit,
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
        onSuccess: (ArrayList<ProductDetail>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = apiApi.getListProductSale()
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getAllPros(
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<ProductDetail>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = apiApi.getAllProducts()
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getListProductCategory(
        cate: String,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<ProductDetail>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = apiApi.getListProductOfCategory(cate)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getProductDetailByID(
        productID: Int,
        onPrepared: () -> Unit,
        onSuccess: (ProductDetail?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = apiApi.getProductDetailByID(productID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getListCategory(
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Category>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = apiApi.getListCategory()
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

    fun getAddress(
        userID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<ShoppingAddress>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.getAddress(userID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getAddressDefault(
        userID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ShoppingAddress?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.getAddressDefault(userID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun addAddress(
        addressRequest: AddressRequest,
        onPrepared: () -> Unit,
        onSuccess: (ResultApi?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.addAddress(addressRequest)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }
    fun deleteAddress(
        addressId: Int,
        onPrepared: () -> Unit,
        onSuccess: (ResultApi?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.deleteAddress(addressId)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }


    fun signUp(
            email: String,
            name: String,
            password: String,
            phone: String,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.signUp(email, name , password, phone)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun changeUserInfo(
            userId: Int,
            name: String,
            phone: String,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.changeInfoAcc(userId, name, phone)
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


    fun addOrder(
            invoiceRequest: InvoiceRequest,
            onPrepared: () -> Unit,
            onSuccess: (ResultApi?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request = apiApi.addOrder(invoiceRequest)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }


    fun getAllOrderItem(
        orderID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Invoice>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.getAllOrder(orderID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }
    fun getCancelOrderItem(
        orderID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Invoice>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.getCancelOrderItem(orderID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }
    fun getAccomplishOrderItem(
            orderID : Int,
            onPrepared: () -> Unit,
            onSuccess: (ArrayList<Invoice>?) -> Unit,
            onError: (String) -> Unit
    ) {
        val request =  apiApi.getAccomplishOrderItem(orderID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }
    fun getConfirmOrderItem(
        orderID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Invoice>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.getConfirmOrderItem(orderID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }
    fun getPaymentOrderItem(
        orderID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Invoice>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.getPaymentOrderItem(orderID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }
    fun getDeliverOrderItem(
        orderID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Invoice>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.getDeliverOrderItem(orderID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun getDetailInvoiceItem(
        invoiceID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<InvoiceDetail>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.getDetailInvoice(invoiceID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

    fun cancelInvoice(
        invoiceID : Int,
        onPrepared: () -> Unit,
        onSuccess: (ResultApi?) -> Unit,
        onError: (String) -> Unit
    ) {
        val request =  apiApi.cancelInvoice(invoiceID)
        onPrepared()
        ApiRequestHelper.asyncRequest(request, onSuccess, onError)
    }

//    fun getCode(
//        email: String,
//        onPrepared: () -> Unit,
//        onSuccess: (String?) -> Unit,
//        onError: (String) -> Unit
//    ){
//        val  request = apiApi.getOtp(email)
//        onPrepared()
//        ApiRequestHelper.asyncRequest(request,onSuccess,onError)
//    }


}