package com.candybytes.taco.data.repositories

import androidx.lifecycle.LiveData
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.vo.Category
import com.candybytes.taco.data.sources.Result

/**
 * Interface to the data layer.
 */
interface CategoriesRepository {

    fun observeCategories() : LiveData<Result<List<Category>>>

    suspend fun getCategories(forceUpdate : Boolean = false) : Result<List<Category>>

    suspend fun refreshCategories()
}