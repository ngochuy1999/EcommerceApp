package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.ResultApi
import com.ngochuy.ecommerce.data.ResultUser
import com.ngochuy.ecommerce.data.User

interface AuthRepository {
    fun login(username: String, password: String): Result<ResultApi>
    fun signUp(
            email: String,
            name: String,
            password: String,
            phone: String,
            address: String
    ): Result<ResultApi>

    fun changeInfo(
            userId: Int,
            email: String,
            name: String,
            phone: String,
            address: String
    ): Result<ResultApi>

    fun forgotPassword(email: String): Result<Int>

    fun changePassword(
            userId: Int,
            oldPass: String,
            newPass: String
    ): Result<ResultApi>

    fun getUserInfoByUserID(userId: Int): Result<ResultUser>
}