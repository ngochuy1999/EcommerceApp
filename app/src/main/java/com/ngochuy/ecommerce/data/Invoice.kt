package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class Invoice(
    val active: Int,
    val address: Any,
    val buyDate: String,
    val customer: Customer,
    val deliveryDate: String,
    val invoiceId: Int,
    val invoiceStatus: InvoiceStatus,
    val name: Any,
    val note: String,
    val phone: Any,
    val updateDate: Any
)