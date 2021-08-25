package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class Account(
    val accountId: Int,
    val active: Int? = null,
    val email: String,
    val isAccuracy: Int? = null,
    val password: String,
    val role: Role,
    val userName: String
)