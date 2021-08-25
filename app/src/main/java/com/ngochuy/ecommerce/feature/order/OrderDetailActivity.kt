package com.ngochuy.ecommerce.feature.order

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityOrderDetailBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.order.adapter.ProductOrderAdapter
import com.ngochuy.ecommerce.roomdb.CartDatabase
import com.ngochuy.ecommerce.roomdb.ProductEntity
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import kotlin.coroutines.CoroutineContext

open class OrderDetailActivity : AppCompatActivity(),CoroutineScope {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }
    val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideOrderViewModelFactory()
        )[OrderViewModel::class.java]
    }

    private var cartDB: CartDatabase?= null

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    lateinit var binding: ActivityOrderDetailBinding
    var productId: Int? = null
    val productAdapter: ProductOrderAdapter by lazy {
        ProductOrderAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        productId = intent.getIntExtra(PRODUCT_ID, -1)
        val userId = getIntPref(USER_ID)
        binding.cartCount = 0
        if (userId != -1){
            mJob = Job()
            cartDB = CartDatabase.getDatabase(this)
            launch {
                val products: List<ProductEntity>? = cartDB?.productDao()?.getAllProduct()
                binding.cartCount= products?.size
            }
        }
        binding.tranFee = 20000
        binding.totalPrice = 0L
        userViewModel.getInfoUser((getIntPref(USER_ID)))
        initViews()
        setEvents()
        bindViewModel()
    }

    private fun initViews() {
        binding.rvProductOrderDetail.adapter = productAdapter
        binding.rvProductOrderDetail.setHasFixedSize(true)
        binding.rvProductOrderDetail.setItemViewCacheSize(20)
    }


    private fun bindViewModel() {


        userViewModel.userInfo.observe(this, {
            binding.user = it
        })


        userViewModel.networkUserInfo.observe(this, {
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

    fun getTotalPrice(pro: Product) {
        var totalPriceCart = 0L

        val discount: Int = pro.sale ?: 0
        val price: Long = pro.price ?: 0
            val priceSale = (price.minus(((discount * 0.01) * price))).times(pro.quantityOrder?: 1)
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
        btnCancelOrder.setOnClickListener {
            binding.tvStatusOrder.setText(R.string.cancelled)
            binding.btnCancelOrder.gone()
        }
    }
}
class OrderDetailAccomplishActivity : OrderDetailActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.getAllOrderItem(getIntPref(USER_ID))
        bindAccViewModel()
    }

    private fun bindAccViewModel(){
        orderViewModel.orderItem.observe(this, {
            var i = 0
            var poistiion = 0
            while (i < it.result?.size!!)
            {
                if( it.result[i].id == productId ) poistiion = i
                i++
            }

            it.result[poistiion].let { it1 -> productAdapter.setProductList(it1) }
            binding.product = it.result[poistiion]
            getTotalPrice(it.result[poistiion])
        })
        binding.tvStatusOrder.setText(R.string.accomplished)
        binding.btnCancelOrder.gone()
    }
}
class OrderDetailConfirmActivity : OrderDetailActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.getConfirmOrderItem(getIntPref(USER_ID))
        bindConViewModel()
    }

    private fun bindConViewModel(){
        orderViewModel.confirmOrderItem.observe(this, {
            var i = 0
            var poistiion = 0
            while (i < it.result?.size!!)
            {
                if( it.result[i].id == productId ) poistiion = i
                i++
            }

            it.result[poistiion].let { it1 -> productAdapter.setProductList(it1) }
            binding.product = it.result[poistiion]
            getTotalPrice(it.result[poistiion])
        })
        binding.tvStatusOrder.setText(R.string.confirm)
    }
}
class OrderDetailDeliveryActivity : OrderDetailActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.getDeliverOrderItem(getIntPref(USER_ID))
        bindDeViewModel()
    }

    private fun bindDeViewModel(){
        orderViewModel.deliverOrderItem.observe(this, {
            var i = 0
            var poistiion = 0
            while (i < it.result?.size!!)
            {
                if( it.result[i].id == productId ) poistiion = i
                i++
            }

            it.result[poistiion].let { it1 -> productAdapter.setProductList(it1) }
            binding.product = it.result[poistiion]
            getTotalPrice(it.result[poistiion])
        })
        binding.tvStatusOrder.setText(R.string.delivery)
        binding.btnCancelOrder.gone()
    }
}
class OrderDetailPaymentActivity : OrderDetailActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderViewModel.getPaymentOrderItem(getIntPref(USER_ID))
        bindPaViewModel()
    }

    private fun bindPaViewModel(){
        orderViewModel.paymentOrderItem.observe(this, {
            var i = 0
            var poistiion = 0
            while (i < it.result?.size!!)
            {
                if( it.result[i].id == productId ) poistiion = i
                i++
            }

            it.result[poistiion].let { it1 -> productAdapter.setProductList(it1) }
            binding.product = it.result[poistiion]
            getTotalPrice(it.result[poistiion])
        })
        binding.tvStatusOrder.setText(R.string.payment)
        binding.btnCancelOrder.gone()
    }
}
