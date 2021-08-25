package com.ngochuy.ecommerce.repository.impl

import androidx.lifecycle.MutableLiveData
import com.ngochuy.ecommerce.data.*
import com.ngochuy.ecommerce.repository.OrderRepository
import com.ngochuy.ecommerce.service.ApiService

class OrderRepositoryImpl(private val apiService: ApiService) : OrderRepository {
    override fun order(
            invoiceRequest: InvoiceRequest,
    ): Result<ResultApi> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<ResultApi>()
        apiService.addOrder(
                invoiceRequest,
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



    override fun getAllOrderItem(orderID: Int): Result<ArrayList<Invoice>> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<ArrayList<Invoice>>()
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
    override fun getConfirmOrderItem(orderID: Int): Result<ArrayList<Invoice>> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<ArrayList<Invoice>>()
        apiService.getConfirmOrderItem(
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
    override fun getPaymentOrderItem(orderID: Int): Result<ArrayList<Invoice>> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<ArrayList<Invoice>>()
        apiService.getPaymentOrderItem(
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
    override fun getDeliverOrderItem(orderID: Int): Result<ArrayList<Invoice>> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<ArrayList<Invoice>>()
        apiService.getDeliverOrderItem(
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

    override fun getDetailInvoice(invoiceId: Int): Result<ArrayList<InvoiceDetail>> {
        val networkState = MutableLiveData<NetworkState>()
        val response = MutableLiveData<ArrayList<InvoiceDetail>>()
        apiService.getDetailInvoiceItem(
            invoiceId,
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