package com.ngochuy.ecommerce.repository.impl

import androidx.lifecycle.MutableLiveData
import com.ngochuy.ecommerce.data.NetworkState
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.ResultApi
import com.ngochuy.ecommerce.repository.CartRepository
import com.ngochuy.ecommerce.service.ApiService


class CartRepositoryImpl(private val apiService: ApiService) : CartRepository {
//    override fun getCartCount(userID: Int): Result<Int> {
//        val networkState = MutableLiveData<NetworkState>()
//        val responseCategories = MutableLiveData<Int>()
//        apiService.getCartCount(
//                userID,
//                onPrepared = {
//                    networkState.postValue(NetworkState.LOADING)
//                },
//                onSuccess = { response ->
//                    responseCategories.value = response
//                    networkState.postValue(NetworkState.LOADED)
//                },
//                onError = { errMessage ->
//                    networkState.postValue(NetworkState.error(errMessage))
//                }
//        )
//
//        return Result(
//                data = responseCategories,
//                networkState = networkState
//        )
//    }
//
//    override fun getProductCart(userID: Int): Result<ArrayList<Product>> {
//        val networkState = MutableLiveData<NetworkState>()
//        val responseProducts = MutableLiveData<ArrayList<Product>>()
//        apiService.getProductCart(
//                userID,
//                onPrepared = {
//                    networkState.postValue(NetworkState.LOADING)
//                },
//                onSuccess = { response ->
//                    responseProducts.value = response ?: arrayListOf()
//                    networkState.postValue(NetworkState.LOADED)
//                },
//                onError = { errMessage ->
//                    networkState.postValue(NetworkState.error(errMessage))
//                }
//        )
//
//        return Result(
//                data = responseProducts,
//                networkState = networkState
//        )
//    }
//
//    override fun plusCart(userID: Int, productID: Int, quantity: Int): Result<ResultApi> {
//        val networkState = MutableLiveData<NetworkState>()
//        val responseCategories = MutableLiveData<ResultApi>()
//        apiService.plusCart(
//                userID, productID, quantity,
//                onPrepared = {
//                    networkState.postValue(NetworkState.LOADING)
//                },
//                onSuccess = { response ->
//                    responseCategories.value = response
//                    networkState.postValue(NetworkState.LOADED)
//                },
//                onError = { errMessage ->
//                    networkState.postValue(NetworkState.error(errMessage))
//                }
//        )
//
//        return Result(
//                data = responseCategories,
//                networkState = networkState
//        )
//    }
//
//    override fun minusCart(userID: Int, productID: Int): Result<Boolean> {
//        val networkState = MutableLiveData<NetworkState>()
//        val responseCategories = MutableLiveData<Boolean>()
//        apiService.minusCart(
//                userID,
//                productID,
//                onPrepared = {
//                    networkState.postValue(NetworkState.LOADING)
//                },
//                onSuccess = { response ->
//                    responseCategories.value = response
//                    networkState.postValue(NetworkState.LOADED)
//                },
//                onError = { errMessage ->
//                    networkState.postValue(NetworkState.error(errMessage))
//                }
//        )
//
//        return Result(
//                data = responseCategories,
//                networkState = networkState
//        )
//    }
//
//    override fun delCart(userID: Int, productID: Int): Result<ResultApi> {
//        val networkState = MutableLiveData<NetworkState>()
//        val responseCategories = MutableLiveData<ResultApi>()
//        apiService.delCart(
//                userID,
//                productID,
//                onPrepared = {
//                    networkState.postValue(NetworkState.LOADING)
//                },
//                onSuccess = { response ->
//                    responseCategories.value = response
//                    networkState.postValue(NetworkState.LOADED)
//                },
//                onError = { errMessage ->
//                    networkState.postValue(NetworkState.error(errMessage))
//                }
//        )
//
//        return Result(
//                data = responseCategories,
//                networkState = networkState
//        )
//    }
//
//    override fun getOtp(email: String): Result<String> {
//        val networkState = MutableLiveData<NetworkState>()
//        val responseOTP = MutableLiveData<String>()
//        apiService.getCode(
//                email,
//                onPrepared = {
//                    networkState.postValue(NetworkState.LOADING)
//                },
//                onSuccess = { response ->
//                    responseOTP.value =response
//                    networkState.postValue(NetworkState.LOADED)
//                },
//                onError = { errMessage ->
//                    networkState.postValue(NetworkState.error(errMessage))
//                }
//        )
//        return  Result(
//                data = responseOTP,
//                networkState = networkState
//        )
//    }
}