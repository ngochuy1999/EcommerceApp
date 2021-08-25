package com.ngochuy.ecommerce.data

data class InvoiceRequest(
    val userId: Int,
    val totalPrice: Long,
    val listProInCart: List<ProductInCart>
)

data class ProductInCart(
    val productId: Int,
    val quantityCart: Int
)