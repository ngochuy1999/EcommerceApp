package com.ngochuy.ecommerce.data

class AddressRequest(
    val userId: Int,
    val active: Int,
    val district: String,
    val isDefault: Int?=0,
    val name: String,
    val phone: String,
    val provice: String,
    val street: String,
    val ward: String
)