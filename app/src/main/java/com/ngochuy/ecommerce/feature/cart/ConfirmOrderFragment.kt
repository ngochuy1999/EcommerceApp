package com.ngochuy.ecommerce.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.OrderItem
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.FragmentConfirmOrderBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.cart.adapter.ProductCartConfirmAdapter
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_cart.*

class ConfirmOrderFragment :Fragment(){
    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }
    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                Injection.provideOrderViewModelFactory()
        )[OrderViewModel::class.java]
    }
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }

    private var listProducts: ArrayList<Product> = arrayListOf()
    private var listItemOrder: ArrayList<OrderItem> = arrayListOf()
    private lateinit var binding: FragmentConfirmOrderBinding
    private val productAdapter: ProductCartConfirmAdapter by lazy {
        ProductCartConfirmAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.getInfoUser(USER_ID)
        cartViewModel.getProductsCart(USER_ID)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.feeShip = 20000
        bindViewModel()
        addEvents()
        initViews()
    }

    private fun initViews() {
        binding.rvProductConfirm.adapter = productAdapter
        binding.rvProductConfirm.setHasFixedSize(true)
        binding.rvProductConfirm.setItemViewCacheSize(20)
    }

    private fun addEvents() {
        binding.btnBackConfirm.setOnClickListener { requireActivity().onBackPressed() }
        binding.btnPaymentConf.setOnClickListener {
            addOrder()
            showOrderSuccess()
        }
    }

    private fun showOrderSuccess() {
        requireActivity().replaceFragment(
                id = R.id.frmCart,
                fragment = OrderSuccessFragment()
        )
    }

    private fun addOrder() {
        orderViewModel.addOrder(USER_ID)
    }

    private fun bindViewModel() {
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            binding.user = it
        }
        cartViewModel.productsCart.observe(viewLifecycleOwner, Observer {
            productAdapter.setProductList(it)
            getTotalPrice(it)
        })

        orderViewModel.dataCheckOut.observe(viewLifecycleOwner) {
            when (it.isStatus) {
                1 -> {
                    showOrderSuccess()
                }
                else -> {
                    Toast.makeText(requireContext(), it.isStatus, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun getTotalPrice(arrProduct: ArrayList<Product>) {
        var totalPriceCart = 0L
        var discount = 0
        var price = 0L
        for (pro in arrProduct) {
            discount = pro.sale ?: 0
            price = pro.price ?: 0
            val priceSale = (price?.minus(((discount * 0.01) * price))).times(pro.quantityOrder?: 1)
            totalPriceCart += priceSale.toLong()
        }
        binding.price = totalPriceCart
    }
}