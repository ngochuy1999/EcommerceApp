package com.ngochuy.ecommerce.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(

    @SerializedName("providerId")
    val providerId: Int? = null,
    @SerializedName("branchName")
    val branchName: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("information")
    val information: String?= null,
    @SerializedName("active")
    val active: Int? = null
): Parcelable