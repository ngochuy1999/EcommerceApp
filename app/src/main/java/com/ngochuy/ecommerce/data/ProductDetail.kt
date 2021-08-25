package com.ngochuy.ecommerce.data

import android.os.Parcelable
import com.ngochuy.ecommerce.base.DynamicSearchAdapter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductDetail(
    var active: Int,
    var addDate: String,
    var color: String,
    var description: Description?,
    var detail: Detail?,
    var imageDetail: ImageDetail?,
    var price: Long,
    var productId: Int,
    var provider: Category?,
    var quantity: Int,
    var sale: Int,
    var title: String,
    var quantityInCart: Int?
): DynamicSearchAdapter.Searchable, Parcelable {
    override fun getSearchCriteria(): String = title?: ""
    override fun toString() = title ?: ""
}