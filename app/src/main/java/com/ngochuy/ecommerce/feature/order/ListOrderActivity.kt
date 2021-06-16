package com.ngochuy.ecommerce.feature.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.databinding.ActivityListOrderBinding
import com.ngochuy.ecommerce.feature.order.adapter.PageAdapter
import kotlinx.android.synthetic.main.activity_list_order.*

class ListOrderActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityListOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_order)

        viewPager.adapter = PageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

}