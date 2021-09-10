package com.ngochuy.ecommerce.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Detail(
    val battery: String,
    val cpu: String,
    val display: String,
    val productId: Int,
    val inch: String,
    val os: String,
    val pixel: String,
    val ram: String,
    val rom: String
): Parcelable