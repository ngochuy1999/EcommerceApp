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
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.dialog_otp.*
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.support.v4.startActivity


class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                Injection.provideCartViewModelFactory()
        )[CartViewModel::class.java]
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
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
        cartViewModel.plusCart(requireContext().getIntPref(USER_ID), id, -1)
        cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
    }

    private fun plusItemCart(id: Int) {
        cartViewModel.plusCart(requireContext().getIntPref(USER_ID), id, 1)
        cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
    }

    private fun delItemCart(id: Int) {
        cartViewModel.delCartItem(requireContext().getIntPref(USER_ID), id)
        cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
        userViewModel.getInfoUser(requireContext().getIntPref(USER_ID))
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
        btnOrderCart.setOnClickListener { showBottomDialogAddCart() }
        swCart.setOnRefreshListener {
            cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
            swCart.isRefreshing = false
        }
    }

    private fun showBottomDialogAddCart() {
        val mBottomSheetDialog = BottomSheetDialog(requireContext())
        val bindingDialog : DialogOtpBinding = DataBindingUtil
                .inflate(LayoutInflater.from(requireContext()), R.layout.dialog_otp, null, false)
        userViewModel.getInfoUser(requireContext().getIntPref(USER_ID))
        bindingDialog.user = userViewModel.userInfo.value
        mBottomSheetDialog.setContentView(bindingDialog.root)
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            bindingDialog.user = it
            it.email?.let { it1 -> cartViewModel.getOTP(it1) }
        }
        cartViewModel.codeOTP.observe(viewLifecycleOwner) {
             OTP = it
        }


        // Add events
        bindingDialog.btnGetOTP.setOnClickListener() {
            if( OTP == bindingDialog.otpView.otp.toString()) {
                bindingDialog.otpView.showSuccess()
                mBottomSheetDialog.dismiss()
                showAddress()
            }else{
                bindingDialog.otpView.showError()
                Toast.makeText(requireContext(),"SAI OTP", Toast.LENGTH_LONG).show()
                bindingDialog.otpView.resetState()
            }

        }
        mBottomSheetDialog.show()
    }

    private fun showAddress() {
        requireActivity().replaceFragment(
                id = R.id.frmCart,
                fragment = ConfirmOrderFragment(),
                addToBackStack = true
        )
    }

    private fun bindViewModel() {
        cartViewModel.productsCart.observe(viewLifecycleOwner, Observer {
            if (it.size != 0) {
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
        var totalPriceCart = 0L
        var discount = 0
        var price = 0L
        for (pro in prosCart) {
            discount = pro.sale ?: 0
            price = pro.price ?: 0
            val priceSale = (price?.minus(((discount * 0.01) * price))).times(pro.quantityOrder
                    ?: 1)
            totalPriceCart += priceSale.toLong()
        }
        bindPrice(tv_price_cart, totalPriceCart)
    }
}
