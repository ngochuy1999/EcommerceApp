package com.ngochuy.ecommerce.feature.order.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.feature.order.AccomplishedFragment
import com.ngochuy.ecommerce.feature.order.DeliveringFragment
import com.ngochuy.ecommerce.feature.order.ItemConfirmFragment
import com.ngochuy.ecommerce.feature.order.PaymentFragment

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 4;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return AccomplishedFragment()
            }
            1 -> {
                return ItemConfirmFragment()
            }
            2 -> {
                return DeliveringFragment()
            }
            3->{
                return PaymentFragment()
            }
            else -> {
                return AccomplishedFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Accomplished"
            }
            1 -> {
                return "Wait Confirm"
            }
            2 -> {
                return "Delivering"
            }
            3-> {
                return "Wait Receive"
            }
        }
        return super.getPageTitle(position)
    }

}