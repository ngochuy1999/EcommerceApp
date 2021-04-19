package com.ngochuy.ecommerce.data

class ResultApi : ArrayList<ResultLoginItem>()

data class ResultLoginItem(
        val message: String,
        val result: Int
)