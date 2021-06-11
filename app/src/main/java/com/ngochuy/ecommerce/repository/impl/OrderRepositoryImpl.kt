package com.ngochuy.ecommerce.repository.impl

import androidx.lifecycle.MutableLiveData
import com.ngochuy.ecommerce.data.*
import com.ngochuy.ecommerce.repository.OrderRepository
import com.ngochuy.ecommerce.service.ApiService

class OrderRepositoryImpl(private val apiService: ApiService) : OrderRepository {
    override fun order(
            userID: Int,
    ): Result<ResultApi> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<ResultApi>()
        apiService.addOrder(
                userID,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { data ->
                    response.value = data
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = response,
                networkState = networkState
        )
    }



    override fun getAllOrderItem(orderID: Int): Result<ResultOrder> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<ResultOrder>()
        apiService.getAllOrderItem(
                orderID,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { data ->
                    response.value = data
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = response,
                networkState = networkState
        )
    }


}