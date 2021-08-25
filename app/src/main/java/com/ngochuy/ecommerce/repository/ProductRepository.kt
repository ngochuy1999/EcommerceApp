package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.*

interface ProductRepository {
    fun getListProductSale(): Result<ArrayList<ProductDetail>>
    fun getListSlide(): Result<ArrayList<Slide>>
    fun getAllListProductSale(): Result<ArrayList<ProductDetail>>
    fun getAllListProduct(): Result<ArrayList<ProductDetail>>
    fun getProductCategory(cate: String): Result<ArrayList<ProductDetail>>
    fun getProductByID(productID: Int): Result<ProductDetail>
//    fun addCart(userId: Int,productID: Int, quantity: Int): Result<ResultApi>
}