package com.ngochuy.ecommerce.ext

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

fun Activity.hideSoftKeyboard(){
    val inputMethodManager =
        getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager

    inputMethodManager?.hideSoftInputFromWindow(
        currentFocus?.windowToken, 0
    )
}