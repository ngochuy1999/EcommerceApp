package com.ngochuy.ecommerce.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.feature.invocie.ComfirmActivity
import kotlinx.android.synthetic.main.fragment_order_success.*
import org.jetbrains.anko.support.v4.startActivity

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
        btn_continue_shopping_dialog.setOnClickListener {
            startActivity<MainActivity>()
            requireActivity().finish()
        }

        btn_view_order.setOnClickListener {
            startActivity<ComfirmActivity>()
            requireActivity().finish()
        }
        requireActivity().onBackPressedDispatcher.addCallback(){
            requireActivity().finish()
        }
    }
}
