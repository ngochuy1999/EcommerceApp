package com.ngochuy.ecommerce.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.InvoiceRequest
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.ProductInCart
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.DialogOtpBinding
import com.ngochuy.ecommerce.databinding.FragmentConfirmOrderBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.cart.adapter.ProductCartConfirmAdapter
import com.ngochuy.ecommerce.roomdb.CartDatabase
import com.ngochuy.ecommerce.roomdb.ProductEntity
import com.ngochuy.ecommerce.viewmodel.CartViewModel
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast
import kotlin.coroutines.CoroutineContext

class ConfirmOrderFragment :Fragment(), CoroutineScope{
//    private val cartViewModel: CartViewModel by lazy {
//        ViewModelProvider(
//                requireActivity(),
//                Injection.provideCartViewModelFactory()
//        )[CartViewModel::class.java]
//    }
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

    private lateinit var binding: FragmentConfirmOrderBinding
    private var totalPrice: Long = 0
    private val productAdapter: ProductCartConfirmAdapter by lazy {
        ProductCartConfirmAdapter()
    }

    private var cartDB: CartDatabase? = null
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.getInfoUser(requireContext().getIntPref(USER_ID))
        //cartViewModel.getProductsCart(requireContext().getIntPref(USER_ID))
        showProductOrder()
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
        }
    }

//    private fun showBottomDialogAddCart() {
//        val mBottomSheetDialog = BottomSheetDialog(requireContext())
//        val bindingDialog : DialogOtpBinding = DataBindingUtil
//                .inflate(LayoutInflater.from(requireContext()), R.layout.dialog_otp, null, false)
//        userViewModel.getInfoUser(requireContext().getIntPref(USER_ID))
//        bindingDialog.user = userViewModel.userInfo.value
//        mBottomSheetDialog.setContentView(bindingDialog.root)
//        userViewModel.userInfo.observe(viewLifecycleOwner) {
//            bindingDialog.user = it
//            it.account.email?.let { it1 -> cartViewModel.getOTP(it1) }
//        }
//        cartViewModel.codeOTP.observe(viewLifecycleOwner) {
//            OTP = it
//        }


        // Add events
//        bindingDialog.btnGetOTP.setOnClickListener() {
//            if( OTP == bindingDialog.otpView.otp.toString()) {
//                bindingDialog.otpView.showSuccess()
//                mBottomSheetDialog.dismiss()
//                addOrder()
//                showOrderSuccess()
//            }else{
//                bindingDialog.otpView.showError()
//                Toast.makeText(requireContext(),"SAI OTP", Toast.LENGTH_LONG).show()
//                bindingDialog.otpView.resetState()
//            }
//
//        }
//        mBottomSheetDialog.show()
//    }

    private fun showOrderSuccess() {
        requireActivity().replaceFragment(
                id = R.id.frmCart,
                fragment = OrderSuccessFragment()
        )
    }

    private fun addOrder() {
        mJob = Job()
        cartDB = CartDatabase.getDatabase(requireContext())
        launch {
            val list: MutableList<ProductInCart> = ArrayList()
            val products: List<ProductEntity>? = cartDB?.productDao()?.getAllProduct()
            if (products != null) {
                for (pro in products){
                    pro.quantityInCart?.let { ProductInCart(pro.productId, it) }?.let { list.add(it) }
                }
            }
            val invoiceRequest = InvoiceRequest(requireContext().getIntPref(USER_ID),totalPrice,list)
            orderViewModel.addOrder(invoiceRequest)
        }
    }

    private fun showProductOrder(){
        mJob = Job()
        cartDB = CartDatabase.getDatabase(requireContext())
        launch {
            val products: List<ProductEntity>? = cartDB?.productDao()?.getAllProduct()
            if (products?.size != 0) {
                productAdapter.setProductList(products!!)
                getTotalPrice(products!!)
            }
        }
    }

    private fun bindViewModel() {
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            binding.user = it
        }
//        cartViewModel.productsCart.observe(viewLifecycleOwner, Observer {
//            productAdapter.setProductList(it)
//            getTotalPrice(it)
//        })

        orderViewModel.dataCheckOut.observe(viewLifecycleOwner) {
            when (it.isStatus) {
                1 -> {
                    mJob = Job()
                    cartDB = CartDatabase.getDatabase(requireContext())
                    launch {
                        cartDB!!.productDao().deleteAllPro()
                    }
                    showOrderSuccess()
                }
                else -> {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun getTotalPrice(arrProduct: List<ProductEntity>) {
        var totalPriceCart = 0L
        var discount = 0
        var price = 0L
        for (pro in arrProduct) {
            discount = pro.sale ?: 0
            price = pro.price ?: 0
            val priceSale = (price?.minus(((discount * 0.01) * price))).times(pro.quantityInCart?: 1)
            totalPriceCart += priceSale.toLong()
        }
        binding.price = totalPriceCart
        totalPrice = totalPriceCart
    }
}