package com.ngochuy.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.data.*
import com.ngochuy.ecommerce.repository.OrderRepository

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {


    private val requestOrder = MutableLiveData<Result<ResultApi>>()

    val dataCheckOut = Transformations.switchMap(requestOrder) {
        it.data
    }
    val networkCheckOut = Transformations.switchMap(requestOrder) {
        it.networkState
    }

    fun addOrder(
            userID: Int,
            name: String,
            phone: String,
            email: String,
            address: String,
            note: String
    ) {
        requestOrder.value = repository.order(userID, name, phone, email, address, note)
    }

    /*         GET ALL STATUS OF ORDER    */
    private val requestStatusOrder = MutableLiveData<Result<ArrayList<OrderStatus>>>()

    val dataStatusOrder = Transformations.switchMap(requestStatusOrder) {
        it.data
    }
    val networkStatusOrder = Transformations.switchMap(requestStatusOrder) {
        it.networkState
    }

    /*         ITEM ORDER   */
    private val requestItemOrder = MutableLiveData<Result<ArrayList<OrderItem>>>()

    val orderItem = Transformations.switchMap(requestItemOrder) {
        it.data
    }
    val networkOrderItem = Transformations.switchMap(requestItemOrder) {
        it.networkState
    }

    fun getAllOrderItem(orderId: Int) {
        requestItemOrder.value = repository.getAllOrderItem(orderId)
    }
}


class OrderViewModelFactory(
        private val repository: OrderRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = OrderViewModel(repository) as T
}