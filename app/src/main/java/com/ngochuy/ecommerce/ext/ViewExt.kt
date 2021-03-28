package com.ngochuy.ecommerce.ext

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisibale(){
    this.visibility = View.INVISIBLE
}
fun View.gone(){
    this.visibility = View.GONE
}
fun View.enable(){
    this.isEnabled = true
}

fun View.disable(){
    this.isEnabled = false
}
fun View.setAutoHideKeyboard(activity: Activity){
    // Set up touch listener for non-text box views to hide keyboard.
    if(this !is EditText){
        setOnTouchListener{ _, _ ->
            activity.hideSoftKeyboard()
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (this is ViewGroup) {
        for (i in 0 until childCount) {
            val innerView = getChildAt(i)
            innerView.setAutoHideKeyboard(activity)
        }
    }
}