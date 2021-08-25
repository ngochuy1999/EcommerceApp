package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class InvoiceDetail(
    val id: Id?,
    val invoice: Invoice?,
    val product: Product?,
    val quantity: Int?
)