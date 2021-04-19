package com.ngochuy.ecommerce.feature.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.ext.replaceFragment


class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        replaceFragment(id = R.id.frmCart, fragment = CartFragment())
    }
}
