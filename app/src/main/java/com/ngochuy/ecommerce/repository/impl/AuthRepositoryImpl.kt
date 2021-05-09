package com.ngochuy.ecommerce.repository.impl

import androidx.lifecycle.MutableLiveData
import com.ngochuy.ecommerce.data.NetworkState
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.ResultApi
import com.ngochuy.ecommerce.data.User
import com.ngochuy.ecommerce.repository.AuthRepository
import com.ngochuy.ecommerce.service.ApiService
import retrofit2.http.Query

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override fun login(username: String, password: String): Result<ResultApi> {
        val networkState = MutableLiveData<NetworkState>()
        val responseLogin = MutableLiveData<ResultApi>()
        apiService.login(
                username, password,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseLogin.value = response
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseLogin,
                networkState = networkState
        )
    }

    override fun signUp(
            email: String,
            name: String,
            password: String,
            phone: String,
            address: String
    ): Result<ResultApi> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSignUp = MutableLiveData<ResultApi>()
        apiService.signUp(
                email, name, password, phone, address, onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseSignUp.value = response
                    networkState.postValue(NetworkState.LOADED)
                }
        ) { errMessage ->
            networkState.postValue(NetworkState.error(errMessage))
        }

        return Result(
                data = responseSignUp,
                networkState = networkState
        )
    }

    override fun changeInfo(
            userId: Int,
            email: String,
            name: String,
            phone: String,
            address: String
    ): Result<ResultApi> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSignUp = MutableLiveData<ResultApi>()
        apiService.changeUserInfo(
                userId, email, name, phone, address,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseSignUp.value = response
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
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
                onSuccess = { response ->
                    responseForgotPassword.value = response
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseForgotPassword,
                networkState = networkState
        )
    }

    override fun changePassword(
            userId: Int,
            oldPass: String,
            newPass: String
    ): Result<ResultApi> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSignUp = MutableLiveData<ResultApi>()
        apiService.changePassword(
                userId, oldPass, newPass,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseSignUp.value = response
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseSignUp,
                networkState = networkState
        )
    }

    override fun getUserInfoByUserID(userId: Int): Result<User> {
        val networkState = MutableLiveData<NetworkState>()
        val responseUser = MutableLiveData<User>()
        apiService.getUserInfoByUserID(
                userId,
                onPrepared = {
                    networkState.postValue(NetworkState.LOADING)
                },
                onSuccess = { response ->
                    responseUser.value = response?.copy()
                    networkState.postValue(NetworkState.LOADED)
                },
                onError = { errMessage ->
                    networkState.postValue(NetworkState.error(errMessage))
                }
        )

        return Result(
                data = responseUser,
                networkState = networkState
        )
    }
}