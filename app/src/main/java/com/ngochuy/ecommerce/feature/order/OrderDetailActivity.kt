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
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityOrderDetailBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.cart.adapter.ProductCartConfirmAdapter
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import com.ngochuy.ecommerce.viewmodel.ProductsViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import java.util.ArrayList

class OrderDetailActivity : AppCompatActivity() {

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
    private val productViewModel: ProductsViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideProductsViewModelFactory()
        )[ProductsViewModel::class.java]
    }


    private lateinit var binding: ActivityOrderDetailBinding
    private var productId: Int? = null
    private var poistiion: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        productId = intent.getIntExtra(PRODUCT_ID, -1)
        if (productId != -1 && productId != null) {
            productViewModel.getProductById(productId!!)
        }
        val userId = getIntPref(USER_ID)
        binding.cartCount = 0
        if (userId != -1)
            cartViewModel.getCartCount(userId)

        binding.tranFee = 20000
        binding.totalPrice = 0L
        userViewModel.getInfoUser((getIntPref(USER_ID)) ?: 0)
        setEvents()
        bindViewModel()
    }


    private fun bindViewModel() {

        cartViewModel.cartCount.observe(this, Observer {
            binding.cartCount = it ?: 0
        })

        userViewModel.userInfo.observe(this) {
            binding.user = it
        }
        productViewModel.product.observe(this, Observer {
            binding.product = it
            getTotalPrice(it)
        })

        userViewModel.networkUserInfo.observe(this, Observer {
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
        })
    }

    private fun getTotalPrice(pro: Product) {
        var totalPriceCart = 0L
        var discount = 0
        var price = 0L
            discount = pro.sale ?: 0
            price = pro.price ?: 0
            val priceSale = (price?.minus(((discount * 0.01) * price))).times(pro.quantityOrder?: 1)
            totalPriceCart += priceSale.toLong()
        binding.totalPrice = totalPriceCart
    }

    private fun setEvents() {
        binding.cartProductDetail.setOnClickListener {
            // Check user login
            if (getIntPref(USER_ID) != -1) startActivity<CartActivity>()
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
