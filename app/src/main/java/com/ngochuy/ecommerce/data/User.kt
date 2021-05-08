package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("address")
        val address: String? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("image")
        val image: String? =null,
        @SerializedName("password")
        val password: String? =null,
        @SerializedName("phone")
        val phone: String? = null,
        @SerializedName("role")
        val role: Boolean? = null,
        @SerializedName("status")
        val status: String? = null,
        @SerializedName("name")
        val username: String? = null
)