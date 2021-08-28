package com.ngochuy.ecommerce.feature.invocie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ngochuy.ecommerce.feature.invocie.*

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 6;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return AllInvoiceFragment()
            }
            1 -> {
                return AccomplishedInvoiceFragment()
            }
            2 -> {
                return ConfirmInvoiceFragment()
            }
            3 -> {
                return DeliveringInvoiceFragment()
            }
            4->{
                return PaymentInvocieFragment()
            }
            5->{
                return CancelInvoiceFragment()
            }
            else -> {
                return AccomplishedInvoiceFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Tất cả đơn hàng"
            }
            1 -> {
                return "Đã giao hàng"
            }
            2 -> {
                return "Chờ xác nhận"
            }
            3 -> {
                return "Đang vận chuyển"
            }
            4-> {
                return "Chờ lấy hàng"
            }
            5-> {
                return "Đã hủy"
            }
        }
        return super.getPageTitle(position)
    }

}