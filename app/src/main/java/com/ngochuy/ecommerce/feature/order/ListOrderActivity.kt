package com.ngochuy.ecommerce.feature.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.databinding.ActivityListOrderBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.USER_ID
import com.ngochuy.ecommerce.ext.getIntPref
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.order.adapter.PageAdapter
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.activity_list_order.*
import org.jetbrains.anko.startActivity

open class OrderActivity : AppCompatActivity()  {

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }

    private lateinit var binding: ActivityListOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_order)

        val userId = getIntPref(USER_ID)
        binding.cartCount = 0
        if (userId != -1) cartViewModel.getCartCount(userId)

        cartViewModel.cartCount.observe(this, {
            binding.cartCount = it ?: 0
        })
        binding.cartProductDetail.setOnClickListener {
            // Check user login
            if (getIntPref(USER_ID) != -1) startActivity<CartActivity>()
            else startActivity<LoginActivity>()
        }
        btnBack.setOnClickListener { finish() }
    }


}
class DeliveringActivity : OrderActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(2, true)
        tabLayout.setupWithViewPager(viewPager)
    }
}
class AccomplishedActivity : OrderActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(0, true)
        tabLayout.setupWithViewPager(viewPager)
    }

}
class ComfirmActivity : OrderActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(1, true)
        tabLayout.setupWithViewPager(viewPager)
    }

}
class PaymentActivity : OrderActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(3, true)
        tabLayout.setupWithViewPager(viewPager)
    }
}