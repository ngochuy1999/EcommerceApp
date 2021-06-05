package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.FragmentSignUpBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.viewmodel.UserViewModel

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            Injection.provideAuthViewModelFactory()
        )[UserViewModel::class.java]
    }
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun onBack(){
        requireActivity().onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        binding.userViewModel = userViewModel
        binding.fragment = this
    }

    private fun bindViewModel() {
        userViewModel.dataRegister.observe(viewLifecycleOwner, Observer {
            when (it.isStatus) {
                1 -> {
                    Toast.makeText(requireContext(),getString(R.string.sign_up), Toast.LENGTH_LONG).show()
                    requireActivity().replaceFragment(fragment = LoginFragment())
                }
                else -> {
                    Toast.makeText(requireContext(), getString(R.string.err_signup), Toast.LENGTH_LONG).show()
                }
            }
        })

        userViewModel.networkStateSignUp.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> binding.progressSignUp.visible()
                Status.SUCCESS -> {
                    binding.progressSignUp.gone()
                }
                Status.FAILED -> {
                    binding.progressSignUp.gone()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
            binding.progressSignUp.visibility =
                if (it.status == Status.RUNNING) View.VISIBLE else View.GONE
        })
    }

    fun onSignUp() {
        var check = true
        val name = binding.edtName.textTrim()
        val phone = binding.edtPhone.textTrim()
        val mail = binding.edtMail.textTrim()
        val address = binding.edtAddress.textTrim()
        val pass = binding.edtPass.textTrim()

        if (name.isEmpty()) {
            binding.edtName.error = getString(R.string.error_input_name_not_entered)
            check = false
        }
        if (phone.isEmpty()) {
            binding.edtPhone.error = getString(R.string.error_input_phone_not_entered)
            check = false
        }
        if (mail.isEmpty()) {
            binding.edtMail.error = getString(R.string.error_input_email_not_entered)
            check = false
        } else if (!isValidEmail(mail)) {
            binding.edtMail.error = getString(R.string.err_email_not_valid)
            check = false
        }
        if (address.isEmpty()) {
            binding.edtAddress.error = getString(R.string.error_input_email_not_entered)
            check = false
        }
        if (pass.isEmpty()) {
            binding.edtPass.error = getString(R.string.error_old_passwords_is_empty)
            check = false
        }

        if (check) {
            userViewModel.signUp(mail, name, pass, phone, address)
        }
    }

}
