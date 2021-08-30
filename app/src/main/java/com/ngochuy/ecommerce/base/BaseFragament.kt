package com.ngochuy.ecommerce.base


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ngochuy.ecommerce.feature.main.MainActivity

abstract class BaseFragment<ViewBinding: ViewDataBinding> : Fragment() {

    lateinit var viewBinding: ViewBinding
    lateinit var navController: NavController

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModelOnce()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }

        bindEvent()
        bindViewModel()
    }

    open fun bindViewModel(){}
    open fun bindEvent(){}
    open fun bindViewModelOnce(){}


}