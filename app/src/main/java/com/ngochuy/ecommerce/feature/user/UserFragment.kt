package com.ngochuy.ecommerce.feature.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.OrderStatus
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.FragmentUserBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.authentication.ChangePassActivity
import com.ngochuy.ecommerce.feature.authentication.LoginActivity
import com.ngochuy.ecommerce.feature.authentication.EditProfileActivity
import com.ngochuy.ecommerce.feature.order.OrderDetailActivity
import com.ngochuy.ecommerce.viewmodel.OrderViewModel
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import org.jetbrains.anko.support.v4.startActivity


/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
                this,
                Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }

    private lateinit var binding: FragmentUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.getInfoUser(USER_ID)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvents()
        initViews()
        bindViewModel()
    }

    private fun initViews() {
    }

    private fun bindViewModel() {
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            binding.user = it
        }

        userViewModel.networkUserInfo.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.RUNNING -> binding.progressUser.visible()
                Status.SUCCESS -> {
                    binding.progressUser.gone()
                }
                Status.FAILED -> {
                    binding.progressUser.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun addEvents() {
        /* imageView.load("") {
             placeholder()
             error()
         }*/
        binding.tvManageOrder.setOnClickListener {
            startActivity<OrderDetailActivity>()
        }

        binding.btnSignOut.setOnClickListener { confirmSignOut() }

        binding.tvHotLine.setOnClickListener {
            callPhoneNumber()
        }

        binding.llInfoLogged.setOnClickListener {
            startActivity<EditProfileActivity>()
        }
        binding.btnChangePass.setOnClickListener {
            startActivity<ChangePassActivity>()
        }

        binding.btnChangeMode.setOnClickListener{
            showDialogChooseModeTheme()
        }
    }


    private fun showDialogChooseModeTheme() {
        val builder = AlertDialog.Builder(requireContext())
        val styles = arrayOf("Light", "Dark", "System Default")
        val checkedItem = requireContext().darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    requireContext().darkMode = 0
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    requireContext().darkMode = 1
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    requireContext().darkMode = 2
                    dialog.dismiss()
                }

            }

//            recreate()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun confirmSignOut() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        with(builder)
        {
            setMessage(getString(R.string.dialogLogOut))
            setPositiveButton(getString(R.string.dialogOk)) { dialog, _ ->
                requireContext().removeValueSharePrefs(USER_EMAIL)
                startActivity<LoginActivity>()
                requireActivity().finish()
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.dialogCancel)) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }

    }

    private fun callPhoneNumber() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CALL_PHONE
                )
                != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.CALL_PHONE
                    )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CALL_PHONE), targetRequestCode
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0966885364"))
            startActivity(intent)
        }
    }


}
