package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("address")
        val address: String? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("idCart")
        val idCart: Int? = null,
        @SerializedName("password")
        val password: String? = null,
        @SerializedName("status")
        val status: Boolean? = null,
        @SerializedName("username")
        val username: String? = null
)