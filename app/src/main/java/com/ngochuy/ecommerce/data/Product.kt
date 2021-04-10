package com.ngochuy.ecommerce.data

import com.google.gson.annotations.SerializedName
import com.ngochuy.ecommerce.base.DynamicSearchAdapter

data class Product(
        @SerializedName("brand")
        val brand: String? = null,
        @SerializedName("color")
        val color: String? = null,
        @SerializedName("description")
        val description: List<String>? = null,
        @SerializedName("details")
        val details: List<String>? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("idProduct")
        val idProduct: String? = null,
        @SerializedName("images")
        val images: List<String>? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("price")
        val price: Float? = null,
        @SerializedName("quantity")
        val quantity: Int? = null,
        @SerializedName("quantityOder")
        val quantityOrder: Int? = null,
        @SerializedName("sale")
        val sale: Int? = null
) : DynamicSearchAdapter.Searchable{
        override fun getSearchCriteria(): String = name?: ""
        override fun toString() = name ?: ""
}
