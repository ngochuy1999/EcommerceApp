package com.ngochuy.ecommerce.repository

import com.ngochuy.ecommerce.data.Category
import com.ngochuy.ecommerce.data.Result

interface CategoryRepository {
    fun getListCategory(): Result<ArrayList<Category>>
}