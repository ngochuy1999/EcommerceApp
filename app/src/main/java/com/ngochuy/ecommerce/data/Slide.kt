package com.ngochuy.ecommerce.data

import com.google.gson.annotations.SerializedName

data class Slide(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?
)