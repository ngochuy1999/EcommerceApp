package com.ngochuy.ecommerce.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.CartType
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.cart.adapter.ProductCartAdapter
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.feature.product.ProductDetailActivity
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.support.v4.startActivity

class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }

    private val cartAdapter: ProductCartAdapter by lazy {
        ProductCartAdapter { id, type -> eventsCart(id, type) }
    }

    private fun eventsCart(id: Int, type: CartType) {
        when (type) {
            CartType.DEL -> delItemCart(id)
            CartType.PLUS -> plusItemCart(id)
            CartType.MINUS -> minusItemCart(id)
            CartType.CLICK -> onClickItemCart(id)
        }
    }

    private fun onClickItemCart(id: Int) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra(PRODUCT_ID, id)
        startActivity(intent)
    }

    private fun minusItemCart(id: Int) {
        cartViewModel.minusCart(USER_ID, id)
        cartViewModel.getProductsCart(USER_ID)
    }

    private fun plusItemCart(id: Int) {
        cartViewModel.plusCart(id, USER_ID, 1)
        cartViewModel.getProductsCart(USER_ID)
    }

    private fun delItemCart(id: Int) {
        cartViewModel.delCartItem(USER_ID, id)
        cartViewModel.getProductsCart(USER_ID)
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel.getProductsCart(USER_ID)
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
        bindViewModel()
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
//        btnOrderCart.setOnClickListener { showAddress() }
        swCart.setOnRefreshListener {
            cartViewModel.getProductsCart(USER_ID)
            swCart.isRefreshing = false
        }
    }

//    private fun showAddress() {
//        requireActivity().replaceFragment(
//                id = R.id.frmCart,
//                fragment = AddressCartFragment(),
//                addToBackStack = true
//        )
//    }

    private fun bindViewModel() {
        cartViewModel.productsCart.observe(viewLifecycleOwner, Observer {
            if (it.size!=0) {
                cartAdapter.setProductList(it)
               setPriceCart(it)
                cartEmpty.gone()
            } else cartEmpty.visible()
        })

        cartViewModel.networkProductsCart.observe(viewLifecycleOwner, Observer {
            progressCart.visibility = if (it.status == Status.RUNNING) View.VISIBLE else View.GONE
        })

        /*cartViewModel.networkPlusCart.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS)
                cartViewModel.getProductsCart(requireActivity().getIntPref(USER_ID))
        })

        cartViewModel.networkMinusCart.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS)
                cartViewModel.getProductsCart(requireActivity().getIntPref(USER_ID))
        })*/
    }

    private fun setPriceCart(prosCart: ArrayList<Product>) {
        var totalPriceCart: Long? = 0
        var discount : Int
        var price :Long
        if (totalPriceCart != null) {
            for (pro in prosCart) {
                discount = pro.sale!!
                price = pro.price!!
                totalPriceCart += (price - price * discount).times(pro.quantity ?: 1)
            }
        }
        bindPrice(tv_price_cart, totalPriceCart)
    }
}
