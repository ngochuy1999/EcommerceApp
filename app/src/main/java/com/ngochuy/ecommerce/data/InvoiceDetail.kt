package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class InvoiceDetail(
    val id: Id?,
    val invoice: Invoice?,
    val product: ProductDetail?,
    val quantity: Int?
)