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
            invoiceRequest: InvoiceRequest,
    ) {
        requestOrder.value = repository.order(invoiceRequest)
    }

    /*         ITEM ORDER   */
    private val requestItemOrder = MutableLiveData<Result<ArrayList<Invoice>>>()

    val orderItem = Transformations.switchMap(requestItemOrder) {
        it.data
    }
    val networkOrderItem = Transformations.switchMap(requestItemOrder) {
        it.networkState
    }

    fun getAllOrderItem(orderId: Int) {
        requestItemOrder.value = repository.getAllOrderItem(orderId)
    }


    /*         ITEM ORDER CONFIRM   */
    private val requestConfirmItemOrder = MutableLiveData<Result<ArrayList<Invoice>>>()

    val confirmOrderItem = Transformations.switchMap(requestConfirmItemOrder) {
        it.data
    }
    val networkConfirmOrderItem = Transformations.switchMap(requestConfirmItemOrder) {
        it.networkState
    }

    fun getConfirmOrderItem(orderId: Int) {
        requestConfirmItemOrder.value = repository.getConfirmOrderItem(orderId)
    }


    /*         ITEM ORDER PAYMENT   */
    private val requestPaymentItemOrder = MutableLiveData<Result<ArrayList<Invoice>>>()

    val paymentOrderItem = Transformations.switchMap(requestPaymentItemOrder) {
        it.data
    }
    val networkPaymentOrderItem = Transformations.switchMap(requestPaymentItemOrder) {
        it.networkState
    }

    fun getPaymentOrderItem(orderId: Int) {
        requestPaymentItemOrder.value = repository.getPaymentOrderItem(orderId)
    }


    /*         ITEM ORDER DELIVER  */
    private val requestDeliverItemOrder = MutableLiveData<Result<ArrayList<Invoice>>>()

    val deliverOrderItem = Transformations.switchMap(requestDeliverItemOrder) {
        it.data
    }
    val networkDeliverOrderItem = Transformations.switchMap(requestDeliverItemOrder) {
        it.networkState
    }

    fun getDeliverOrderItem(orderId: Int) {
        requestDeliverItemOrder.value = repository.getDeliverOrderItem(orderId)
    }

    /*         ITEM DETAIL INVOICE  */
    private val requestDetailInvoice = MutableLiveData<Result<ArrayList<InvoiceDetail>>>()

    val invoiceDetailItem = Transformations.switchMap(requestDetailInvoice) {
        it.data
    }
    val networkInvoiceDetailItem = Transformations.switchMap(requestDetailInvoice) {
        it.networkState
    }

    fun getDetailInvoiceItem(invoiceId: Int) {
        requestDetailInvoice.value = repository.getDetailInvoice(invoiceId)
    }

}


class OrderViewModelFactory(
        private val repository: OrderRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = OrderViewModel(repository) as T
}