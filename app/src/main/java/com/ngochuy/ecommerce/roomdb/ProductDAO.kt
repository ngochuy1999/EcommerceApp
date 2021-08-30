package com.ngochuy.ecommerce.roomdb

import androidx.room.*

@Dao
interface ProductDAO {

    @Query("SELECT * FROM product ORDER BY productId ASC")
    suspend fun getAllProduct(): List<ProductEntity>

    @Query("SELECT SUM(quantityInCart) FROM product")
    suspend fun cartCount(): Int

    @Query("SELECT * FROM product WHERE productId = :proId")
    suspend fun findProductById(proId: Int): ProductEntity

    @Query("DELETE FROM product")
    suspend fun deleteAllPro()

    @Insert
    suspend fun insert(productEntity: ProductEntity)

    @Update
    suspend fun update(productEntity: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)
}