package com.ngochuy.ecommerce.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ngochuy.ecommerce.data.Category
import com.ngochuy.ecommerce.data.Description
import com.ngochuy.ecommerce.data.Detail

@Entity(tableName = "product")
class ProductEntity(
    @PrimaryKey var productId: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "sale") var sale: Int,
    @ColumnInfo(name = "price") var price: Long,
    @ColumnInfo(name = "quantity") var quantity: Int,
    @ColumnInfo(name = "imageDetail") var imageDetail: String?,
    @ColumnInfo(name = "quantityInCart") var quantityInCart: Int?
)