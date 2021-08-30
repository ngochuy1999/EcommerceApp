package com.ngochuy.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngochuy.ecommerce.data.ShoppingAddress

class SharedViewModel : ViewModel() {
    val selectedAddress = MutableLiveData<ShoppingAddress>()

    fun select(item: ShoppingAddress) {
        selectedAddress.value = item
    }
}