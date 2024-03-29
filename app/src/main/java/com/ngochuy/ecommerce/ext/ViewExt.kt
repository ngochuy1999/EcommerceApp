package com.ngochuy.ecommerce.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

/*
fun <T> ViewGroup.addViewExt(
    context: Context, @LayoutRes layoutItem: Int,
    itemList: ArrayList<T>,
    orientation: Int = LinearLayout.HORIZONTAL,
    listener: (itemView: View?, item: T, position: Int) -> Unit
) {
    this.removeAllViews()
    val param = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    val childGroup = LinearLayout(context)
    childGroup.orientation = orientation
    childGroup.layoutParams = param
    itemList.forEachIndexed { index, item ->
        AsyncLayoutInflater(context).inflate(layoutItem, this) { view, _, _ ->
            view.apply {
                listener.invoke(view, item, index)
            }
            childGroup.addView(view)
        }
    }
    this.addView(childGroup)
}*/
/*

fun View.slide() {
    val anim = if (isVisible)
        AnimationUtils.loadAnimation(context, R.anim.slide_down)
    else
        AnimationUtils.loadAnimation(context, R.anim.slide_up)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            isVisible = !isVisible
        }

        override fun onAnimationStart(p0: Animation?) {
        }

    })

    startAnimation(anim)
}
*/

/*
fun View.slideUp() {
    if (isVisible) return

    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_up)
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            isVisible = true
        }

        override fun onAnimationStart(p0: Animation?) {
        }

    })

    startAnimation(anim)
}*/
