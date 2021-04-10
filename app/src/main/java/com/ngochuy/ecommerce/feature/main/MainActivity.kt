package com.ngochuy.ecommerce.feature.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.feature.search.SearchFragment
import com.ngochuy.ecommerce.feature.category.CategoryFragment
import com.ngochuy.ecommerce.feature.home.HomeFragment
import com.ngochuy.ecommerce.feature.main.adapter.MyFragmentPagerAdapter
import com.ngochuy.ecommerce.feature.user.UserFragment

class MainActivity : AppCompatActivity() , ViewPager.OnPageChangeListener {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                botNavigation.selectedItemId = R.id.bot_nav_home
            }
            1 -> {
                botNavigation.selectedItemId = R.id.bot_nav_category
            }
            2 -> {
                botNavigation.selectedItemId = R.id.bot_nav_search
            }
            3 -> {
                botNavigation.selectedItemId = R.id.bot_nav_user
            }
        }
    }

    // set icon selected: home
    var idMenuSelected: Int = R.id.bot_nav_home
    val homeFragment by lazy {
        HomeFragment()
    }
    val searchFragment by lazy {
        SearchFragment()
    }
    val categoryFragment by lazy {
        CategoryFragment()
    }
    val userFragment by lazy {
        UserFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(homeFragment, "Home fragment")
        viewPagerAdapter.addFragment(categoryFragment, "Category fragment")
        viewPagerAdapter.addFragment(searchFragment, "Search fragment")
        viewPagerAdapter.addFragment(userFragment, "User fragment")

        viewPagerMain.adapter = viewPagerAdapter

        //viewPagerMain.setOffscreenPageLimit(1)

        viewPagerMain.addOnPageChangeListener(this)

        val checkShowSearch = intent.getBooleanExtra("search", false)
        if(checkShowSearch){
            viewPagerMain.currentItem = 2
        }

        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bot_nav_home -> {
                    viewPagerMain.currentItem = 0
                    true
                }
                R.id.bot_nav_category -> {
                    viewPagerMain.currentItem = 1
                    true
                }
                R.id.bot_nav_search -> {
                    viewPagerMain.currentItem = 2
                    true
                }
                R.id.bot_nav_user -> {
                    viewPagerMain.currentItem = 4
                    true
                }
                else -> false
            }
        }
    }

    fun showCategoryFragment(view: View) {
        viewPagerMain.currentItem = 1
    }

    fun showSearchFragment(view: View) {
        viewPagerMain.currentItem = 2
    }
}
