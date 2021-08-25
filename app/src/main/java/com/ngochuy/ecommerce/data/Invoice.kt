package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class Invoice(
    val active: Int,
    val address: String?,
    val buyDate: String,
    val customer: Customer,
    val deliveryDate: String,
    val invoiceId: Int?,
    val invoiceStatus: InvoiceStatus,
    val name: String?,
    val note: String,
    val phone: String?,
    val totalPrice: Long?,
    val updateDate: Any
)