package com.ngochuy.ecommerce.feature.product.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.ngochuy.ecommerce.R
import java.util.ArrayList

class SlidingImageProductDetailAdapter (
    private val context: Context,
    private var arrAdv: ArrayList<String>,
) : PagerAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return arrAdv.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.slidingimages_productdetail, view, false)!!

        val imageView = imageLayout.findViewById(R.id.image_product) as ImageView

        Glide.with(context)
            .load("https://hoanghamobile.com/i/preview/Uploads/"+arrAdv[position])
            .into(imageView)

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }
}

