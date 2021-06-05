package com.ngochuy.ecommerce.data


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("brands")
    val name: ArrayList<String>?,

)