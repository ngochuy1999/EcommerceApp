package com.ngochuy.ecommerce.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.InvoiceRequest
import com.ngochuy.ecommerce.data.ProductInCart
import com.ngochuy.ecommerce.data.ShoppingAddress
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.FragmentConfirmOrderBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.addressbook.AddressBookFragment
import com.ngochuy.ecommerce.feature.addressbook.ShippingAddressDetailFragment
import com.ngochuy.ecommerce.feature.cart.adapter.ProductCartConfirmAdapter
import com.ngochuy.ecommerce.roomdb.CartDatabase
import com.ngochuy.ecommerce.roomdb.ProductEntity
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import com.ngochuy.ecommerce.viewmodel.SharedViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_confirm_order.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.toast
import kotlin.coroutines.CoroutineContext

class ConfirmOrderFragment :Fragment(), CoroutineScope{

    private val sharedModel: SharedViewModel by activityViewModels()

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
    private var addressDf : ShoppingAddress? = null
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
        showProductOrder()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConfirmOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.getAddressDefault(requireContext().getIntPref(USER_ID))
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
        shipping_address.setOnClickListener {
            requireActivity().addFragment(
                id = R.id.frmCart,
                fragment = AddressBookFragment()
            )
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
            val invoiceRequest = addressDf?.let {
                InvoiceRequest(requireContext().getIntPref(USER_ID),
                    it.name,it.phone,it.getFullAddress(),totalPrice,list)
            }
            if (invoiceRequest != null) {
                orderViewModel.addOrder(invoiceRequest)
            }else{Toast.makeText(requireContext(), "Chọn địa chỉ giao hàng", Toast.LENGTH_SHORT).show()}
        }
    }

    private fun showProductOrder(){
        mJob = Job()
        cartDB = CartDatabase.getDatabase(requireContext())
        launch {
            val products: List<ProductEntity>? = cartDB?.productDao()?.getAllProduct()
            if (products?.size != 0) {
                productAdapter.setProductList(products!!)
                getTotalPrice(products)
            }
        }
    }

    private fun bindViewModel() {

        userViewModel.addressDefault.observe(viewLifecycleOwner) {
            binding.shippingAddress.item = it
            addressDf = it
        }

        sharedModel.selectedAddress.observe(viewLifecycleOwner) {
            binding.shippingAddress.item = it
            addressDf = it
        }

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

        orderViewModel.networkCheckOut.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.RUNNING -> progressOr.visible()
                Status.SUCCESS -> {
                    progressOr.gone()
                }
                Status.FAILED -> {
                    progressOr.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun getTotalPrice(arrProduct: List<ProductEntity>) {
        var totalPriceCart = 0L
        var discount: Int
        var price: Long
        for (pro in arrProduct) {
            discount = pro.sale
            price = pro.price
            val priceSale = (price.minus(((discount * 0.01) * price))).times(pro.quantityInCart?: 1)
            totalPriceCart += priceSale.toLong()
        }
        binding.price = totalPriceCart
        totalPrice = totalPriceCart
    }
}