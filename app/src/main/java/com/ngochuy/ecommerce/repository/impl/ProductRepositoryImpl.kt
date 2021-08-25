package com.ngochuy.ecommerce.repository.impl

import androidx.lifecycle.MutableLiveData
import com.ngochuy.ecommerce.data.*
import com.ngochuy.ecommerce.repository.ProductRepository
import com.ngochuy.ecommerce.service.ApiService


class ProductRepositoryImpl(private val apiService: ApiService) : ProductRepository {

    override fun getListProductSale(): Result<ArrayList<ProductDetail>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProducts = MutableLiveData<ArrayList<ProductDetail>>()
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

    override fun getAllListProductSale(): Result<ArrayList<ProductDetail>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProducts = MutableLiveData<ArrayList<ProductDetail>>()
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

    override fun getAllListProduct(): Result<ArrayList<ProductDetail>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProducts = MutableLiveData<ArrayList<ProductDetail>>()
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

    override fun getProductCategory(cate: String): Result<ArrayList<ProductDetail>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProducts = MutableLiveData<ArrayList<ProductDetail>>()
        apiService.getListProductCategory(
                cate,
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

    override fun getProductByID(productID: Int): Result<ProductDetail> {
        val networkState = MutableLiveData<NetworkState>()
        val responseProduct = MutableLiveData<ProductDetail>()
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

//    override fun addCart(userId: Int, productID: Int, quantity: Int): Result<ResultApi> {
//        val networkState = MutableLiveData<NetworkState>()
//        val responseAddCart = MutableLiveData<ResultApi>()
//        apiService.addCart(
//                userId,
//                productID,
//                quantity,
//                onPrepared = {
//                    networkState.postValue(NetworkState.LOADING)
//                },
//                onSuccess = { response ->
//                    responseAddCart.value = response
//                    networkState.postValue(NetworkState.LOADED)
//                },
//                onError = { errMessage ->
//                    networkState.postValue(NetworkState.error(errMessage))
//                }
//        )
//        return Result(
//                data = responseAddCart,
//                networkState = networkState
//        )
//    }
}