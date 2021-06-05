package com.ngochuy.ecommerce.feature.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityMainBinding
import com.ngochuy.ecommerce.ext.USER_ID
import com.ngochuy.ecommerce.ext.hideSoftKeyboard
import com.ngochuy.ecommerce.ext.showKeyBoard
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.search.SearchFragment
import com.ngochuy.ecommerce.feature.category.CategoryFragment
import com.ngochuy.ecommerce.feature.home.HomeFragment
import com.ngochuy.ecommerce.feature.main.adapter.MyFragmentPagerAdapter
import com.ngochuy.ecommerce.feature.user.UserFragment
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.PRODUCT_ID
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.ll_cart.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() , ViewPager.OnPageChangeListener {
    private lateinit var binding: ActivityMainBinding

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                botNavigation.selectedItemId = R.id.bot_nav_home
                hideSoftKeyboard()
            }
            1 -> {
                botNavigation.selectedItemId = R.id.bot_nav_category
                hideSoftKeyboard()
            }
            2 -> {
                botNavigation.selectedItemId = R.id.bot_nav_search
                showKeyBoard()
            }
            3 -> {
                botNavigation.selectedItemId = R.id.bot_nav_user
                hideSoftKeyboard()
            }
        }
    }

    private val homeFragment by lazy {
        HomeFragment()
    }
    private val searchFragment by lazy {
        SearchFragment()
    }
    private val categoryFragment by lazy {
        CategoryFragment()
    }
    private val userFragment by lazy {
        UserFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

            // Set cart count, check user is login yet? check by get userID from Shared pref
        val userId = USER_ID
        binding.cartCount = 0
        if (userId != -1)
            cartViewModel.getCartCount(userId)
        hideSoftKeyboard()
        addViewPager()
        bindViewModel()
        addBotNavEvents()
        addEvents()
    }

    private fun addEvents() {
        viewPagerMain.addOnPageChangeListener(this)
        edtSearchMain.setOnClickListener {
            viewPagerMain.currentItem = 2
            edtSearchMain.requestFocus()
        }
        edtSearchMain.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchFragment.eventSearch(s.toString())
            }
        })
        binding.cartProductDetail.setOnClickListener {
            // Check user login
            if (USER_ID != -1) startActivity<CartActivity>()
            else startActivity<LoginActivity>()
        }
    }

    private fun addBotNavEvents() {
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bot_nav_home -> {
                    edtSearchMain.requestFocus()
                    viewPagerMain.currentItem = 0
                    hideSoftKeyboard()
                    true
                }
                R.id.bot_nav_category -> {
                    viewPagerMain.currentItem = 1
                    hideSoftKeyboard()
                    true
                }
                R.id.bot_nav_search -> {
                    viewPagerMain.currentItem = 2
                    showKeyBoard()
                    true
                }
                R.id.bot_nav_user -> {
                    viewPagerMain.currentItem = 4
                    hideSoftKeyboard()
                    true
                }
                else -> false
            }
        }
    }

    private fun addViewPager() {
        val viewPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(homeFragment, "Home fragment")
        viewPagerAdapter.addFragment(categoryFragment, "Category fragment")
        viewPagerAdapter.addFragment(searchFragment, "Search fragment")
        viewPagerAdapter.addFragment(userFragment, "User fragment")
        viewPagerMain.adapter = viewPagerAdapter
    }
    private fun bindViewModel() {

        cartViewModel.cartCount.observe(this, Observer {
            binding.cartCount = it ?: 0
        })
    }

}
