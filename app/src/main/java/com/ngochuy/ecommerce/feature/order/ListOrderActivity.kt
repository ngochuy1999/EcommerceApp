package com.ngochuy.ecommerce.feature.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.databinding.ActivityListOrderBinding
import com.ngochuy.ecommerce.feature.order.adapter.PageAdapter
import kotlinx.android.synthetic.main.activity_list_order.*

class DeliveringActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityListOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_order)

        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(2,false)
        tabLayout.setupWithViewPager(viewPager)
    }

}
class AccomplishedActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityListOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_order)

        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(0,false)
        tabLayout.setupWithViewPager(viewPager)
    }

}
class ComfirmActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityListOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_order)

        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(1,false)
        tabLayout.setupWithViewPager(viewPager)
    }

}
class PaymentActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityListOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_order)

        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(3,false)
        tabLayout.setupWithViewPager(viewPager)
    }

}