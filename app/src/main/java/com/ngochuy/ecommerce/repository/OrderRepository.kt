package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.*

interface OrderRepository {
    fun order(invoiceRequest: InvoiceRequest): Result<ResultApi>
    fun getAllOderItem(orderID: Int): Result<ArrayList<Invoice>>
    fun getCancelOderItem(orderID: Int): Result<ArrayList<Invoice>>
    fun getAccomplishOrderItem(orderID: Int): Result<ArrayList<Invoice>>
    fun getConfirmOrderItem(orderID: Int): Result<ArrayList<Invoice>>
    fun getPaymentOrderItem(orderID: Int): Result<ArrayList<Invoice>>
    fun getDeliverOrderItem(orderID: Int): Result<ArrayList<Invoice>>
    fun getDetailInvoice(invoiceId: Int): Result<ArrayList<InvoiceDetail>>
    fun cancelInvoice(invoiceId: Int): Result<ResultApi>
}