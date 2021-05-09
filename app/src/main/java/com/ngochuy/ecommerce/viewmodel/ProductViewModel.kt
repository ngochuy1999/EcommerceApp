package com.ngochuy.ecommerce.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.repository.ProductRepository

class ProductsViewModel(private val repository: ProductRepository) : ViewModel() {

    private val requestAllProductSale = MutableLiveData<Result<ArrayList<Product>>>()
    private val requestAllProducts = MutableLiveData<Result<ArrayList<Product>>>()
    private val requestAllProductOfCategory = MutableLiveData<Result<ArrayList<Product>>>()
    private val requestProduct = MutableLiveData<Result<Product>>()
    private val requestAddCart = MutableLiveData<Result<Boolean>>()

    val listProductSale = Transformations.switchMap(requestAllProductSale) {
        it.data
    }

    val networkStateProSale = Transformations.switchMap(requestAllProductSale) {
        it.networkState
    }

    fun getAllListProductSale() {
        requestAllProductSale.value = repository.getAllListProductSale()
    }

    val listProducts = Transformations.switchMap(requestAllProducts) {
        it.data
    }

    val networkStateAllPros = Transformations.switchMap(requestAllProducts) {
        it.networkState
    }

    fun getAllProducts() {
        requestAllProducts.value = repository.getAllListProduct()
    }

    val listProductCategory = Transformations.switchMap(requestAllProductOfCategory) {
        it.data
    }

    val networkStateProCategory = Transformations.switchMap(requestAllProductOfCategory) {
        it.networkState
    }

    fun getProductCategory(cateId: Int) {
        requestAllProductOfCategory.value = repository.getProductCategory(cateId)
    }

    val product = Transformations.switchMap(requestProduct) {
        it.data
    }

    val networkProduct = Transformations.switchMap(requestProduct) {
        it.networkState
    }

    fun getProductById(productId: Int) {
        requestProduct.value = repository.getProductByID(productId)
    }

    val networkStateAddCart = Transformations.switchMap(requestAddCart) {
        it.networkState
    }

    fun addCart( userId: Int,productId: Int, quantity: Int) {
        requestAddCart.value = repository.addCart(userId,productId,quantity)
    }


}


class ProductsViewModelFactory(
        private val repository: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = ProductsViewModel(repository) as T
}