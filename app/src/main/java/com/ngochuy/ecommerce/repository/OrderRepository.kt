package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.*

interface OrderRepository {
    fun order(invoiceRequest: InvoiceRequest): Result<ResultApi>
    fun getAllOrderItem(orderID: Int): Result<ResultOrder>
    fun getConfirmOrderItem(orderID: Int): Result<ResultOrder>
    fun getPaymentOrderItem(orderID: Int): Result<ResultOrder>
    fun getDeliverOrderItem(orderID: Int): Result<ResultOrder>
}