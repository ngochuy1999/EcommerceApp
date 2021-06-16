package com.ngochuy.ecommerce.data

import com.google.gson.annotations.SerializedName

data class ResultOrder(
        @SerializedName("result")
        val result: ArrayList<Product>? = null,
        @SerializedName("isStatus")
        val isStatus: Int? = null
)