package com.ngochuy.ecommerce.data

import com.google.gson.annotations.SerializedName

data class Slide(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val image: String?,
    @SerializedName("")
    val productID: Int?
)