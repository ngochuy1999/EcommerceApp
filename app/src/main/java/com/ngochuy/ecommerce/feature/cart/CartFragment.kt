package com.ngochuy.ecommerce.feature.cart

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.CartType
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.DialogOtpBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.cart.adapter.ProductCartAdapter
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.roomdb.CartDatabase
import com.ngochuy.ecommerce.roomdb.ProductEntity
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.dialog_otp.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread
import kotlin.coroutines.CoroutineContext


class CartFragment : Fragment(), CoroutineScope {

//    private val cartViewModel: CartViewModel by lazy {
//        ViewModelProvider(
//                requireActivity(),
//                Injection.provideCartViewModelFactory()
//        )[CartViewModel::class.java]
//    }

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
//        cartViewModel.plusCart(requireContext().getIntPref(USER_ID), id, -1)
//        cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
        launch {
            item?.let {
                ProductEntity(
                    it.productId,
                    it.title,
                    it.sale,
                    it.price,
                    it.quantity,
                    it.imageDetail,
                    it.quantityInCart?.plus(-1)
                )
            }
                ?.let { cartDB?.productDao()?.update(it) }
            // Get cart again
            bindViewModel()
        }
    }

    private fun plusItemCart(item: ProductEntity) {
//        cartViewModel.plusCart(requireContext().getIntPref(USER_ID), id, 1)
//        cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
        launch {
            item?.let {
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
//        cartViewModel.delCartItem(requireContext().getIntPref(USER_ID), id)
//        cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
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
                            setPriceCart(products!!)
                            cartEmpty.gone()
                    }else cartEmpty.visible()
                }

//        cartViewModel.networkProductsCart.observe(viewLifecycleOwner, Observer {
//            progressCart.visibility = if (it.status == Status.RUNNING) View.VISIBLE else View.GONE
//        })

        /*cartViewModel.networkPlusCart.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS)
                cartViewModel.getProductsCart(requireActivity().getIntPref(USER_ID))
        })

        cartViewModel.networkMinusCart.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS)
                cartViewModel.getProductsCart(requireActivity().getIntPref(USER_ID))
        })*/
    }

    private fun setPriceCart(prosCart: List<ProductEntity>) {
        var totalPriceCart = 0L
        var discount = 0
        var price = 0L
        for (pro in prosCart) {
            discount = pro.sale ?: 0
            price = pro.price ?: 0
            val priceSale = (price?.minus(((discount * 0.01) * price))).times(pro.quantityInCart
                    ?: 1)
            totalPriceCart += priceSale.toLong()
        }
        bindPrice(tv_price_cart, totalPriceCart)
    }
}
