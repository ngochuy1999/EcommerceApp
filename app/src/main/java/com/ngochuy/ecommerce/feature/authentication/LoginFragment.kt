package com.ngochuy.ecommerce.feature.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.databinding.FragmentLoginBinding
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.ext.*
import com.ngochuy.ecommerce.feature.main.MainActivity
import com.ngochuy.ecommerce.viewmodel.UserViewModel
import org.jetbrains.anko.support.v4.startActivity
import java.util.concurrent.Executor

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
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
        executor = ContextCompat.getMainExecutor(requireContext())

        setPrompt()

        if (isBiometricHardWareAvailable(requireContext())) {

            //Enable the button if the device has biometric hardware available
            binding.fingerprintIv.isEnabled = true

            initBiometricPrompt(
                    BIOMETRIC_AUTHENTICATION,
                    BIOMETRIC_AUTHENTICATION_SUBTITLE,
                    BIOMETRIC_AUTHENTICATION_DESCRIPTION,
                    false
            )
        } else {
            binding.fingerprintIv.isEnabled = false

            //Fallback, use device password/pin
            if (deviceHasPasswordPinLock(requireContext())) {
                binding.fingerprintIv.isEnabled = true

                initBiometricPrompt(
                        PASSWORD_PIN_AUTHENTICATION,
                        PASSWORD_PIN_AUTHENTICATION_SUBTITLE,
                        PASSWORD_PIN_AUTHENTICATION_DESCRIPTION,
                        true
                )
            }
        }
    }

    private fun setPrompt() {
        biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        Snackbar.make(
                                binding.myCoordinatorLayout,
                                errString,
                                Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)
                        Snackbar.make(
                                binding.myCoordinatorLayout,
                                R.string.btn_ok_verifi,
                                Snackbar.LENGTH_SHORT
                        ).show()
                        startActivity<MainActivity>()
                        requireActivity().finish()
                        requireContext().setIntPref(USER_ID,requireContext().getIntPref(USERID_TOUCHID))
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Snackbar.make(
                                binding.myCoordinatorLayout,
                                R.string.wrong_fingerprint,
                                Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    private fun initBiometricPrompt(
            title: String,
            subtitle: String,
            description: String,
            setDeviceCred: Boolean
    ) {
        if (setDeviceCred) {
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    .setSubtitle(subtitle)
                    .setDescription(description)
                    .setDeviceCredentialAllowed(true)
                    .build()
        } else {
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    .setSubtitle(subtitle)
                    .setDescription(description)
                    .setNegativeButtonText(CANCEL)
                    .build()
        }


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
                    Toast.makeText(requireContext(), getString(R.string.error_login), Toast.LENGTH_LONG).show()
                }
                1 -> {
                    Toast.makeText(requireContext(), getString(R.string.login), Toast.LENGTH_LONG).show()
                    if(requireContext().getIntPref(USERID_TOUCHID) != it.id)
                        requireContext().setBooleanPref(CHECK_FINGER,false)
                    requireContext().setIntPref(USER_ID,it.id)
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
        else if (!isValidEmail(username)) {
            binding.edtUserSignIn.error = getString(R.string.err_email_not_valid)
            check = false
        }
        if (pass.isEmpty()) {
            binding.edtPasswordSignIn.error = getString(R.string.err_pass_empty)
            check = false
        }
        if (check) {
            userViewModel.login(username, pass)
        }
    }
    fun onClickSignUp() {
        requireActivity().addFragment(fragment = SignUpFragment())
    }
    fun onClickFigger(){
        if (requireContext().getBooleanPref(CHECK_FINGER)){
            biometricPrompt.authenticate(promptInfo)
        }else{
            Snackbar.make(
                binding.myCoordinatorLayout,
                R.string.set_touchid,
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }

}
