package com.ngochuy.ecommerce.ext

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("textDiscount")
fun bindTextDiscount(view: TextView, discount: Int?) {
    view.text = "-" + discount.toString() + "%"
}


@BindingAdapter(value = ["price", "discount"], requireAll = false)
fun bindTextPrice(view: TextView, price: Long?, discount: Int) {
    val localeVN = Locale("vi", "VN")
    val currencyVN = NumberFormat.getCurrencyInstance(localeVN)
    val priceSale = price?.minus(((discount * 0.01) * price))
    view.text = currencyVN.format(priceSale?:0)
}

@BindingAdapter("txtPrice")
fun bindPrice(view: TextView, price: Long?) {
    val localeVN = Locale("vi", "VN")
    val currencyVN = NumberFormat.getCurrencyInstance(localeVN)
    view.text = currencyVN.format(price?:0)
}

@BindingAdapter("txtPriceDiscount")
fun bindPriceDiscount(view: TextView, price: Long?) {
    val localeVN = Locale("vi", "VN")
    val currencyVN = NumberFormat.getCurrencyInstance(localeVN)
    view.text = currencyVN.format(price?:0)
    view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

@BindingAdapter("productStock")
fun bindProductCount(view: TextView, stock: Int) {
    val strStock: String
    if (stock > 0) {
        strStock =  "$stock sản phẩm sẵn có"
        view.setTextColor(Color.parseColor("#0C0C0C"))
    }
    else {
        strStock= "Hết hàng"
        view.setTextColor(Color.parseColor("#F44336"))
    }
    view.text = strStock
}
@BindingAdapter("enableBtnAddCart")
fun bindBtnAddCart(view: TextView, stock: Int) {
    view.isEnabled = stock>0
}
@BindingAdapter("txtCartCount")
fun bindTextCartCount(view: TextView, counter: Int) {
    if (counter > 0) {
        view.visible()
        view.text = counter.toString()
    }
    else view.gone()
}

@BindingAdapter("setVisible")
fun setVisible(view: View, isVisible: Boolean?) {
    view.isVisible = isVisible ?: false
}