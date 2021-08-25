package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.*

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

    fun getUserInfoByUserID(userId: Int): Result<User>
}