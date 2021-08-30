package com.ngochuy.ecommerce.feature.invocie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.databinding.ActivityListOrderBinding
import com.ngochuy.ecommerce.ext.USER_ID
import com.ngochuy.ecommerce.ext.getIntPref
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.invocie.adapter.PageAdapter
import com.ngochuy.ecommerce.roomdb.CartDatabase
import com.ngochuy.ecommerce.roomdb.ProductEntity
import kotlinx.android.synthetic.main.activity_list_order.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import kotlin.coroutines.CoroutineContext

open class OrderActivity : AppCompatActivity(),CoroutineScope  {

    private var cartDB: CartDatabase?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    private lateinit var binding: ActivityListOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_order)

        val userId = getIntPref(USER_ID)
        binding.cartCount = 0
        if (userId != -1){
            mJob = Job()
            cartDB = CartDatabase.getDatabase(this)
            launch {
                val cart = cartDB?.productDao()?.cartCount()
                if(cart == null){
                    binding.cartCount = 0
                }else
                    binding.cartCount = cart
            }
        }
        binding.cartProductDetail.setOnClickListener {
            // Check user login
            if (getIntPref(USER_ID) != -1) startActivity<CartActivity>()
            else startActivity<LoginActivity>()
        }
        btnBack.setOnClickListener { finish() }
    }


}
class AllInvocieActivity : OrderActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(0, true)
        tabLayout.setupWithViewPager(viewPager)
    }
}
class CancelInvoiceActivity : OrderActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(5, true)
        tabLayout.setupWithViewPager(viewPager)
    }
}
class DeliveringActivity : OrderActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(3, true)
        tabLayout.setupWithViewPager(viewPager)
    }
}
class AccomplishedActivity : OrderActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(1, true)
        tabLayout.setupWithViewPager(viewPager)
    }

}
class ComfirmActivity : OrderActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(2, true)
        tabLayout.setupWithViewPager(viewPager)
    }

}
class PaymentActivity : OrderActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = PageAdapter(supportFragmentManager)
        viewPager.setCurrentItem(4, true)
        tabLayout.setupWithViewPager(viewPager)
    }
}