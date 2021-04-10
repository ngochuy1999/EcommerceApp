package com.ngochuy.ecommerce.ext

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.math.RoundingMode
import java.text.DecimalFormat


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
fun bindTextPrice(view: TextView, price: Float?, discount: Int) {
    val df = DecimalFormat("#,###,###")
    df.roundingMode = RoundingMode.CEILING
    val priceSale = price?.minus(((discount * 0.01) * price))
    val priceSelling = df.format(priceSale) + " đ"
    view.text = priceSelling
}
@BindingAdapter("txtPrice")
fun bindPrice(view: TextView, price: Float?) {
    val df = DecimalFormat("#,###,###")
    df.roundingMode = RoundingMode.CEILING
    val priceSelling = df.format(price) + " đ"
    view.text = priceSelling
    view.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
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