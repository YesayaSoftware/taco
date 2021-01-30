package com.candybytes.taco.data.sources

import androidx.lifecycle.LiveData
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.di.ServiceModule
import com.candybytes.taco.vo.Category

/**
 * Main entry point for accessing tasks data.
 */
interface CategoryDataSource {
    fun observeCategories(): LiveData<Result<List<Category>>>

    suspend fun getCategories(service: TacoService): Result<List<Category>>

    suspend fun refreshCategories(service: TacoService)

    suspend fun saveCategory(service: TacoService, category: Category)

    suspend fun deleteAllCategories(service: TacoService)
}