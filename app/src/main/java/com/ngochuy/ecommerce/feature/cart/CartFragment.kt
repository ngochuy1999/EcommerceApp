package com.ngochuy.ecommerce.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.CartType
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.cart.adapter.ProductCartAdapter
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.roomdb.CartDatabase
import com.ngochuy.ecommerce.roomdb.ProductEntity
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.startActivity
import kotlin.coroutines.CoroutineContext


class CartFragment : Fragment(), CoroutineScope {

    private val cartAdapter: ProductCartAdapter by lazy {
        ProductCartAdapter { item, type -> eventsCart(item, type) }
    }
    private var cartDB: CartDatabase? = null
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    private fun eventsCart(item: ProductEntity, type: CartType) {
        when (type) {
            CartType.DEL -> delItemCart(item)
            CartType.PLUS -> plusItemCart(item)
            CartType.MINUS -> minusItemCart(item)
            CartType.CLICK -> onClickItemCart(item)
        }
    }

    private fun onClickItemCart(item: ProductEntity) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra(PRODUCT_ID, item.productId)
        startActivity(intent)
    }

    private fun minusItemCart(item: ProductEntity) {
        launch {
            item.let {
                ProductEntity(
                    it.productId,
                    it.title,
                    it.sale,
                    it.price,
                    it.quantity,
                    it.imageDetail,
                    it.quantityInCart?.plus(-1)
                )
            }.let { cartDB?.productDao()?.update(it) }
            // Get cart again
            bindViewModel()
        }
    }

    private fun plusItemCart(item: ProductEntity) {
        launch {
            item.let {
                ProductEntity(
                    it.productId,
                    it.title,
                    it.sale,
                    it.price,
                    it.quantity,
                    it.imageDetail,
                    it.quantityInCart?.plus(1)
                )
            }.let { cartDB?.productDao()?.update(it) }
            // Get cart again
            bindViewModel()
        }
    }

    private fun delItemCart(item: ProductEntity) {
        launch {
            cartDB?.productDao()?.delete(item)
            // Get cart again
            bindViewModel()
        }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvents()
        initViews()
    }

    private fun initViews() {
        rvProductCart.adapter = cartAdapter
        rvProductCart.setHasFixedSize(true)
        rvProductCart.setItemViewCacheSize(20)
    }

    private fun addEvents() {
        btnBackCart.setOnClickListener { requireActivity().onBackPressed() }
        btn_continue_shopping_cart.setOnClickListener { startActivity<MainActivity>() ; requireActivity().finish()}
        btnOrderCart.setOnClickListener { showConfirmOrderFragment() }
        swCart.setOnRefreshListener {
            bindViewModel()
            swCart.isRefreshing = false
        }
    }

    private fun showConfirmOrderFragment() {
        requireActivity().replaceFragment(
                id = R.id.frmCart,
                fragment = ConfirmOrderFragment(),
                addToBackStack = true
        )
    }

    private fun bindViewModel() {
                mJob = Job()
                cartDB = CartDatabase.getDatabase(requireContext())
                launch {
                    val products: List<ProductEntity>? = cartDB?.productDao()?.getAllProduct()
                    if (products?.size != 0) {
                            cartAdapter.setProductList(products!!)
                            setPriceCart(products)
                            cartEmpty.gone()
                    }else cartEmpty.visible()
                }

    }

    private fun setPriceCart(prosCart: List<ProductEntity>) {
        var totalPriceCart = 0L
        var discount: Int
        var price: Long
        for (pro in prosCart) {
            discount = pro.sale
            price = pro.price
            val priceSale = (price.minus(((discount * 0.01) * price))).times(pro.quantityInCart
                    ?: 1)
            totalPriceCart += priceSale.toLong()
        }
        bindPrice(tv_price_cart, totalPriceCart)
    }
}
