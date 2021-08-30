package com.ngochuy.ecommerce.data

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoppingAddress(
    val active: Int,
    val addressId: Int,
    val district: String,
    val isDefault: Int?=0,
    val name: String,
    val phone: String,
    val provice: String,
    val street: String,
    val ward: String
) : BaseObservable(), Parcelable {
    fun getFullAddress(): String {
        val fullAddress = StringBuilder()
        if (!street.isNullOrEmpty())
            fullAddress.append(street)
        if (!ward.isNullOrEmpty())
            fullAddress.append(", $ward")
        if (!district.isNullOrEmpty())
            fullAddress.append(", $district")
        if (!provice.isNullOrEmpty())
            fullAddress.append(", $provice")
        return fullAddress.toString()
    }

    fun isEmpty(): Boolean {
        return district.isNullOrEmpty()
                || name.isNullOrEmpty()
                || phone.isNullOrEmpty()
                || provice.isNullOrEmpty()
                || street.isNullOrEmpty()
                || ward.isNullOrEmpty()
    }
}