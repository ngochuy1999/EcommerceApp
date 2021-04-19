package com.ngochuy.ecommerce.repository.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ngochuy.ecommerce.data.NetworkState
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.Slide
import com.ngochuy.ecommerce.repository.ProductRepository
import com.ngochuy.ecommerce.service.ApiService


class ProductRepositoryImpl(private val apiService: ApiService) : ProductRepository {

    override fun getListProductSale(): Result<ArrayList<Product>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProducts = MutableLiveData<ArrayList<Product>>()
        apiService.getListProductSale(
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseProducts.value = response ?: arrayListOf()
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )
        return Result(
                data = responseProducts,
                networkState = networkState
        )
    }

    override fun getListSlide(): Result<ArrayList<Slide>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSlides = MutableLiveData<ArrayList<Slide>>()
        apiService.getListSlide(
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseSlides.value = response ?: arrayListOf()
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )
        return Result(
                data = responseSlides,
                networkState = networkState
        )
    }

    override fun getAllListProductSale(): Result<ArrayList<Product>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProducts = MutableLiveData<ArrayList<Product>>()
        apiService.getAllListProductSale(
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseProducts.value = response ?: arrayListOf()
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseProducts,
                networkState = networkState
        )
    }

    override fun getAllListProduct(): Result<ArrayList<Product>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProducts = MutableLiveData<ArrayList<Product>>()
        apiService.getAllPros(
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseProducts.value = response ?: arrayListOf()
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseProducts,
                networkState = networkState
        )
    }

    override fun getProductCategory(cateId: Int): Result<ArrayList<Product>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProducts = MutableLiveData<ArrayList<Product>>()
        apiService.getListProductCategory(
                cateId,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseProducts.value = response ?: arrayListOf()
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseProducts,
                networkState = networkState
        )
    }

    override fun getProductByID(productID: Int): Result<Product> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProduct = MutableLiveData<Product>()
        apiService.getProductDetailByID(
                productID,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    if (response != null)
                        responseProduct.value = response.copy()
                    else responseProduct.value = null
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )
        return Result(
                data = responseProduct,
                networkState = networkState
        )
    }

    override fun addCart(productID: Int, userId: Int, quantity: Int): Result<Boolean> {
        val networkState = MutableLiveData<NetworkState>()
        val responseAddCart = MutableLiveData<Boolean>()
        apiService.addCart(
                productID,
                userId,
                quantity,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseAddCart.value = response
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )
        return Result(
                data = responseAddCart,
                networkState = networkState
        )
    }
}