package com.candybytes.taco.data.sources.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.data.sources.CategoryDataSource
import com.candybytes.taco.data.sources.Result
import com.candybytes.taco.di.ServiceModule
import com.candybytes.taco.vo.Category

/**
 * Implementation of the data source that adds a latency simulating network.
 */
class CategoriesRemoteDataSource : CategoryDataSource {

    init {
        this.observeCategories()
    }

    private val observeCategories = MutableLiveData<Result<List<Category>>>()

    override fun observeCategories(): LiveData<Result<List<Category>>> {
        return observeCategories
    }

    override suspend fun getCategories(service: TacoService): Result<List<Category>> {
        val categories = service.getAllCategoriesAsync().await()

        return try {
            Result.Success(categories)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun refreshCategories(service: TacoService) {
        observeCategories.value = this.getCategories(service)
    }

    override suspend fun saveCategory(service: TacoService, category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllCategories(service: TacoService) {
        TODO("Not yet implemented")
    }
}