package com.ngochuy.ecommerce.feature.order

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.activity_order_detail.view.*
import kotlinx.android.synthetic.main.ll_cart.*
import org.jetbrains.anko.startActivity
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Order
import com.ngochuy.ecommerce.data.OrderItem
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityOrderDetailBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.ORDER
import com.ngochuy.ecommerce.ext.USER_ID
import com.ngochuy.ecommerce.ext.gone
import com.ngochuy.ecommerce.ext.visible
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.cart.adapter.ProductCartConfirmAdapter
import com.ngochuy.ecommerce.feature.order.adapter.ProductOrderAdapter
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import java.util.ArrayList

class OrderDetailActivity : AppCompatActivity() {
    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideOrderViewModelFactory()
        )[OrderViewModel::class.java]
    }
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }
    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }
    private val productAdapter: ProductOrderAdapter by lazy {
        ProductOrderAdapter()
    }

    private lateinit var binding: ActivityOrderDetailBinding
    private var order: Order? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        order = intent.getParcelableExtra(ORDER)
        val userId = USER_ID
        binding.cartCount = 0
        if (userId != -1)
            cartViewModel.getCartCount(userId)
        //order?.id?.let {
            //orderViewModel.getAllOrder(it)
        binding.order = order
        binding.tranFee = 20000
        binding.totalPrice = 0L
        //}
        orderViewModel.getAllOrderItem(USER_ID)
        userViewModel.getInfoUser((USER_ID) ?: 0)
        setEvents()
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        binding.rvProductOrderDetail.adapter = productAdapter
        binding.rvProductOrderDetail.setHasFixedSize(true)
        binding.rvProductOrderDetail.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {

        cartViewModel.cartCount.observe(this, Observer {
            binding.cartCount = it ?: 0
        })

        userViewModel.userInfo.observe(this) {
            binding.user = it
        }
        orderViewModel.orderItem.observe(this) {
            productAdapter.setProductList(it.result ?: arrayListOf())
            getTotalPrice(it.result ?: arrayListOf())
        }

        orderViewModel.networkOrderItem.observe(this) {
            when (it.status) {
                Status.RUNNING -> progressOrderDetail.visible()
                Status.SUCCESS -> {
                    progressOrderDetail.gone()
                }
                Status.FAILED -> {
                    progressOrderDetail.gone()
                    Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getTotalPrice(list: ArrayList<Product>) {
        var totalPriceCart = 0L
        var discount = 0
        var price = 0L
        for (pro in list) {
            discount = pro.sale ?: 0
            price = pro.price ?: 0
            val priceSale = (price?.minus(((discount * 0.01) * price))).times(pro.quantityOrder?: 1)
            totalPriceCart += priceSale.toLong()
        }
        binding.totalPrice = totalPriceCart
    }

    private fun setEvents() {
        binding.cartProductDetail.setOnClickListener {
            // Check user login
            if (USER_ID != -1) startActivity<CartActivity>()
            else startActivity<LoginActivity>()
        }
        btnBack.setOnClickListener { finish() }
//        btnCancelOrder.setOnClickListener {
//            order?.id?.let { it1 -> orderViewModel.cancelOrder(it1) }
//            order?.id = 4
//            binding.tvStatusOrder.text="Da huy"
//            binding.btnCancelOrder.gone()
//        }
    }
}
