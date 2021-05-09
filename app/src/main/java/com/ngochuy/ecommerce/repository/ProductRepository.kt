package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.Slide

interface ProductRepository {
    fun getListProductSale(): Result<ArrayList<Product>>
    fun getListSlide(): Result<ArrayList<Slide>>
    fun getAllListProductSale(): Result<ArrayList<Product>>
    fun getAllListProduct(): Result<ArrayList<Product>>
    fun getProductCategory(cateId: Int): Result<ArrayList<Product>>
    fun getProductByID(productID: Int): Result<Product>
    fun addCart(userId: Int,productID: Int, quantity: Int): Result<Boolean>
}