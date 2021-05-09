package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.FragmentLoginBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import org.jetbrains.anko.support.v4.startActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindingViewModel()
    }

    fun onBack() {
        requireActivity().onBackPressed()
    }

    private fun initView() {
        binding.userViewModel = userViewModel
        binding.fragment = this
    }

    private fun bindingViewModel() {
        userViewModel.resultLogin.observe(viewLifecycleOwner, Observer {
            when (it.isStatus) {
                0 -> {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                }
                1 -> {
                    //      Toast.makeText(requireContext(), "ok", Toast.LENGTH_LONG).show()
                    USER_ID = it.id
                    startActivity<MainActivity>()
                    requireActivity().finish()
                }
            }
        })

        userViewModel.networkStateLogin.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> binding.progressLogin.visible()
                Status.SUCCESS -> {
                    binding.progressLogin.gone()
                }
                Status.FAILED -> {
                    binding.progressLogin.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun onClickLogin() {
        var check = true
        val username = binding.edtUserSignIn.textTrim()
        val pass = binding.edtPasswordSignIn.textTrim()
        if (username.isEmpty()) {
            binding.edtUserSignIn.error = getString(R.string.error_input_email_not_entered)
            check = false
        }
//        else if (!isValidEmail(email)) {
//            binding.edtEmailSignIn.error = getString(R.string.err_email_not_valid)
//            check = false
//        }
        if (pass.isEmpty()) {
            binding.edtPasswordSignIn.error = getString(R.string.error_old_passwords_is_empty)
            check = false
        }
        if (check) {
            userViewModel.login(username, pass)
        }
    }

}
