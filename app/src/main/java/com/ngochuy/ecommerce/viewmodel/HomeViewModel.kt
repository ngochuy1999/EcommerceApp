package com.ngochuy.ecommerce.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.Slide
import com.ngochuy.ecommerce.repository.ProductRepository


class HomeViewModel(private val repository: ProductRepository) : ViewModel() {

    private val requestSlide = MutableLiveData<Result<ArrayList<Slide>>>()
    private val requestProductSale = MutableLiveData<Result<ArrayList<Product>>>()

    init {
    //    getListSlide()
        getListProductSale()
    }

    fun refresh(){
    //    getListSlide()
        getListProductSale()
    }

    val listSlides = Transformations.switchMap(requestSlide) {
        it.data
    }

    val networkStateSlide = Transformations.switchMap(requestSlide) {
        it.networkState
    }

//    fun getListSlide() {
//        requestSlide.value = repository.getListSlide()
//    }

    val listProductSale = Transformations.switchMap(requestProductSale) {
        it.data
    }

    val networkStateProSale = Transformations.switchMap(requestProductSale) {
        it.networkState
    }

    fun getListProductSale() {
        requestProductSale.value = repository.getListProductSale()
    }
}


class HomeViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = HomeViewModel(repository) as T
}