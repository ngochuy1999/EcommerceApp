package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.data.User

interface AuthRepository {
    fun login(email: String, password: String) : Result<Boolean>
    fun signUp(user: User) : Result<Boolean>
    fun forgotPassword(email: String) : Result<Boolean>
}