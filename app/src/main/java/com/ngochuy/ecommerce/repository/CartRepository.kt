package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Result

interface CartRepository {
    fun getCartCount(userID: Int):  Result<Int>
    fun getProductCart(userID: Int): Result<ArrayList<Product>>
    fun plusCart(userID: Int, productID: Int,quantity: Int): Result<Boolean>
    fun minusCart(userID: Int, productID: Int): Result<Boolean>
    fun delCart(userID: Int, productID: Int): Result<Boolean>
}