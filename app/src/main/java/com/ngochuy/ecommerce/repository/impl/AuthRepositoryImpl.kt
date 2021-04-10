package com.ngochuy.ecommerce.repository.impl

import androidx.lifecycle.MutableLiveData
import com.ngochuy.ecommerce.data.NetworkState
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.User
import com.ngochuy.ecommerce.repository.AuthRepository
import com.ngochuy.ecommerce.service.ApiService

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override fun login(email: String, password: String): Result<Int> {
        val networkState = MutableLiveData<NetworkState>()
        val responseLogin = MutableLiveData<Int>()
        apiService.login(
                email, password,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = {response ->
                    responseLogin.value = response
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = {errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseLogin,
                networkState = networkState
        )
    }

    override fun signUp(user: User): Result<Int> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSignUp = MutableLiveData<Int>()
        apiService.signUp(
                user,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = {response ->
                    responseSignUp.value = response
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = {errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseSignUp,
                networkState = networkState
        )
    }

    override fun forgotPassword(email: String): Result<Int> {
        val networkState = MutableLiveData<NetworkState>()
        val responseForgotPassword = MutableLiveData<Int>()
        apiService.forgotPassword(
                email,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = {response ->
                    responseForgotPassword.value = response
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = {errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseForgotPassword,
                networkState = networkState
        )
    }

}