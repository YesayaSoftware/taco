package com.candybytes.taco.data.sources.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.data.db.CategoryDao
import com.candybytes.taco.data.sources.CategoryDataSource
import com.candybytes.taco.data.sources.Result
import com.candybytes.taco.vo.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Concrete implementation of a data source as a db.
 */
class CategoriesLocalDataSource internal constructor(
    private val categoryDao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryDataSource {
    override fun observeCategories(): LiveData<Result<List<Category>>> {
        return categoryDao.observeCategories().map { categories ->
            Result.Success(categories)
        }
    }

    override suspend fun getCategories(service: TacoService): Result<List<Category>>  = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(categoryDao.getAllAsync())
        } catch (e : Exception) {
            Result.Error(e)
        }
    }

    override suspend fun refreshCategories(service: TacoService) {
        TODO("Not yet implemented")
    }

    override suspend fun saveCategory(service: TacoService, category: Category) {
        categoryDao.insertAsync(category)
    }

    override suspend fun deleteAllCategories(service: TacoService) {
        categoryDao.deleteCategories()
    }
}