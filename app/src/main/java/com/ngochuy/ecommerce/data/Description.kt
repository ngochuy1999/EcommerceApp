package com.ngochuy.ecommerce.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Description(
    val des1: String,
    val des2: String,
    val descriptionId: Int,
    val image: String
): Parcelable