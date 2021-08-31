package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class Customer(
    val account: Account,
    val dateCreate: String,
    val imageUrl: String,
    val name: String,
    val phone: String,
    val userId: Int
)