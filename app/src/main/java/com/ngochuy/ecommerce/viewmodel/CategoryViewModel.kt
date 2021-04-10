package com.ngochuy.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngochuy.ecommerce.data.Category
import com.ngochuy.ecommerce.data.Result
import com.ngochuy.ecommerce.repository.CategoryRepository

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val requestCategories = MutableLiveData<Result<ArrayList<Category>>>()

    val listCategory= Transformations.switchMap(requestCategories) {
        it.data
    }

    val networkStateCategory = Transformations.switchMap(requestCategories) {
        it.networkState
    }

    fun getListCategories() {
        requestCategories.value = repository.getListCategory()
    }
}

class CategoryViewModelFactory(
    private val repository: CategoryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = CategoryViewModel(repository) as T
}