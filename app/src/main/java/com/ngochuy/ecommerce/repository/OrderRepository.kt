package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.*

interface OrderRepository {
    fun order(userID: Int,
              name: String,
              phone: String,
              email: String,
              address: String,
              note: String): Result<ResultApi>
    fun getAllOrderItem(orderID: Int): Result<ArrayList<OrderItem>>
}