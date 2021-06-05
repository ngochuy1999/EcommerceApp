package com.ngochuy.ecommerce.repository.impl

import androidx.lifecycle.MutableLiveData
import com.ngochuy.ecommerce.data.Category
import com.ngochuy.ecommerce.data.NetworkState
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.repository.CategoryRepository
import com.ngochuy.ecommerce.service.ApiService

class CategoryRepositoryImpl(private val apiService: ApiService) : CategoryRepository {
    override fun getListCategory(): Result<ArrayList<String>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseCategories = MutableLiveData<ArrayList<String>>()
        apiService.getListCategory(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseCategories.value = response ?: arrayListOf()
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )

        return Result(
            data = responseCategories,
            networkState = networkState
        )
    }

}