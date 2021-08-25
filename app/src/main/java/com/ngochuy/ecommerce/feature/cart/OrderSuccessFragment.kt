package com.ngochuy.ecommerce.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.feature.order.ComfirmActivity
import com.ngochuy.ecommerce.feature.order.OrderDetailActivity
import com.ngochuy.ecommerce.roomdb.CartDatabase
import kotlinx.android.synthetic.main.fragment_order_success.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.startActivity
import kotlin.coroutines.CoroutineContext

class OrderSuccessFragment : Fragment(){

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvents()
    }

    private fun addEvents() {
        btnBackOrderSuccess.setOnClickListener { startActivity<MainActivity>() }
        btn_continue_shopping_dialog.setOnClickListener { startActivity<MainActivity>() }
        btn_view_order.setOnClickListener {
            startActivity<ComfirmActivity>()
            requireActivity().finish()
        }
    }
}
