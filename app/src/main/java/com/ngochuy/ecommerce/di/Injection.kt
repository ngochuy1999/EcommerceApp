package com.ngochuy.ecommerce.di

import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.repository.*
import com.ngochuy.ecommerce.repository.impl.*
import com.ngochuy.ecommerce.service.ApiManager
import com.ngochuy.ecommerce.service.ApiService
import com.ngochuy.ecommerce.viewmodel.*

object Injection {


    /*----------------- api service -----------------*/

    private fun provideApiService(): ApiService {
        return ApiService(ApiManager.create())
    }

    /*----------------- Repository -----------------*/

    private fun provideProductRepository(): ProductRepository {
        return ProductRepositoryImpl(provideApiService())
    }

    private fun provideOrderRepository(): OrderRepository {
        return OrderRepositoryImpl(provideApiService())
    }

    private fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(provideApiService())
    }

    private fun provideCategoryRepository(): CategoryRepository {
        return CategoryRepositoryImpl(provideApiService())
    }

    /*----------------- View model factory -----------------*/

    fun provideHomeViewModelFactory(): ViewModelProvider.Factory {
        return HomeViewModelFactory(provideProductRepository())
    }

    fun provideOrderViewModelFactory(): ViewModelProvider.Factory {
        return OrderViewModelFactory(provideOrderRepository())
    }

    fun provideAuthViewModelFactory(): ViewModelProvider.Factory {
        return UserViewModelFactory(provideAuthRepository())
    }

    fun provideCategoryViewModelFactory(): ViewModelProvider.Factory {
        return CategoryViewModelFactory(provideCategoryRepository())
    }

    fun provideProductsViewModelFactory(): ViewModelProvider.Factory {
        return ProductsViewModelFactory(provideProductRepository())
    }

}