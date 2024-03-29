package com.ngochuy.ecommerce.feature.invocie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.ActivityOrderDetailBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.feature.cart.CartActivity
import com.ngochuy.ecommerce.feature.invocie.adapter.ProductOrderAdapter
import com.ngochuy.ecommerce.roomdb.CartDatabase
import com.ngochuy.ecommerce.roomdb.ProductEntity
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast
import kotlin.coroutines.CoroutineContext

open class InvoiceDetailActivity : AppCompatActivity(),CoroutineScope {

    private val orderViewModel: OrderViewModel by lazy {
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
    private var invoiceId: Int? = null

    private val productAdapter: ProductOrderAdapter by lazy {
        ProductOrderAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        invoiceId = intent.getIntExtra(INVOICE_ID, -1)
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
        orderViewModel.getDetailInvoiceItem(invoiceId!!)
        bindingViewModel()
        initViews()
        setEvents()
    }

    private fun bindingViewModel(){
        orderViewModel.invoiceDetailItem.observe(this, {
            productAdapter.setProductList(it?: arrayListOf())
            binding.invoice = it[0].invoice
            if (it[0].invoice?.invoiceStatus?.statusId==1){
                binding.btnCancelOrder.visible()
            }
        })
        orderViewModel.networkInvoiceDetailItem.observe(this, {
            when (it.status) {
                Status.RUNNING -> progressOrderDetail.visible()
                Status.SUCCESS -> {
                    progressOrderDetail.gone()
                }
                Status.FAILED -> {
                    progressOrderDetail.gone()
                    it.msg?.let { it1 -> toast(it1) }
                }
            }
        })

        orderViewModel.networkCancelOrderItem.observe(this, {
            when (it.status) {
                Status.RUNNING -> progressOrderDetail.visible()
                Status.SUCCESS -> {
                    progressOrderDetail.gone()
                }
                Status.FAILED -> {
                    progressOrderDetail.gone()
                    it.msg?.let { it1 -> toast(it1) }
                }
            }
        })

        orderViewModel.cancelInvoice.observe(this,{
            when(it.isStatus){
                1 -> {
                    btnCancelOrder.gone()
                    tvStatusOrder.text = getText(R.string.cancel_order)
                    toast("Đã hủy hóa đơn")
                }
                0 -> toast("error")
            }
        })
    }
    private fun initViews() {
        binding.rvProductInvoiceDetail.adapter = productAdapter
        binding.rvProductInvoiceDetail.setHasFixedSize(true)
        binding.rvProductInvoiceDetail.setItemViewCacheSize(20)
    }


    private fun setEvents() {
        binding.cartProductDetail.setOnClickListener {
            // Check user login
            if (getIntPref(USER_ID) != -1) startActivity<CartActivity>()
            else startActivity<LoginActivity>()
        }
        btnBack.setOnClickListener { finish() }
        btnCancelOrder.setOnClickListener {
            invoiceId?.let { orderViewModel.cancelInvoice(it) }
        }
    }
}
