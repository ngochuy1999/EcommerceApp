package com.ngochuy.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.data.*
import com.ngochuy.ecommerce.repository.OrderRepository

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    // Add Order

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

    /*         ITEM ALL INVOICE   */
    private val requestAllOrder = MutableLiveData<Result<ArrayList<Invoice>>>()

    val allOrderItem = Transformations.switchMap(requestAllOrder) {
        it.data
    }
    val networkAllOrderItem = Transformations.switchMap(requestAllOrder) {
        it.networkState
    }

    fun getAllOrderItem(orderId: Int) {
        requestAllOrder.value = repository.getAllOderItem(orderId)
    }

    /*         ITEM CANCEL ORDER   */
    private val requestCancelOrder = MutableLiveData<Result<ArrayList<Invoice>>>()

    val cancelOderItem = Transformations.switchMap(requestCancelOrder) {
        it.data
    }
    val networkCancelOrderItem = Transformations.switchMap(requestCancelOrder) {
        it.networkState
    }

    fun getCancelOrderItem(orderId: Int) {
        requestCancelOrder.value = repository.getCancelOderItem(orderId)
    }

    /*         ITEM ACCOMPLISH ORDER   */
    private val requestItemOrder = MutableLiveData<Result<ArrayList<Invoice>>>()

    val orderItem = Transformations.switchMap(requestItemOrder) {
        it.data
    }
    val networkOrderItem = Transformations.switchMap(requestItemOrder) {
        it.networkState
    }

    fun getAccomplishOrderItem(orderId: Int) {
        requestItemOrder.value = repository.getAccomplishOrderItem(orderId)
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

    /*         CANCEL INVOICE  */
    private val requestCancelInvocie = MutableLiveData<Result<ResultApi>>()

    val cancelInvoice = Transformations.switchMap(requestCancelInvocie) {
        it.data
    }
    val networkCancelInvoice = Transformations.switchMap(requestCancelInvocie) {
        it.networkState
    }

    fun cancelInvoice(invoiceId: Int) {
        requestCancelInvocie.value = repository.cancelInvoice(invoiceId)
    }

}


class OrderViewModelFactory(
        private val repository: OrderRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = OrderViewModel(repository) as T
}