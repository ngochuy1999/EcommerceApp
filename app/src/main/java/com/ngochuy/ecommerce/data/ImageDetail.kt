package com.ngochuy.ecommerce.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageDetail(
    val productId: Int,
    val imageUrl1: String,
    val imageUrl2: String,
    val imageUrl3: String,
    val imageUrl4: String,
    val imageUrl5: String
): Parcelable