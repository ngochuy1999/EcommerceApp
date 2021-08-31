package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class User(
    val account: Account,
    val dateCreate: String,
    val imageUrl: String? =null,
    val name: String,
    val phone: String,
    val userId: Int
)