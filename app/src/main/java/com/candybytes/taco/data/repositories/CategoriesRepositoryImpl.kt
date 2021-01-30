package com.candybytes.taco.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.candybytes.taco.data.sources.CategoryDataSource
import com.candybytes.taco.data.sources.Result
import com.candybytes.taco.vo.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CategoriesRepositoryImpl(
    private val categoriesRemoteDataSource: CategoryDataSource,
    private val categoriesLocalDataSource: CategoryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoriesRepository {

    override fun observeCategories(): LiveData<Result<List<Category>>> {
        return categoriesLocalDataSource.observeCategories()
    }

    override suspend fun getCategories(forceUpdate: Boolean): Result<List<Category>> {
        if (forceUpdate) {
            try {
                updateCategoriesFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return categoriesLocalDataSource.getCategories()
    }

    override suspend fun refreshCategories() {
        TODO("Not yet implemented")
    }

    private suspend fun updateCategoriesFromRemoteDataSource() {
        val remoteCategories = categoriesRemoteDataSource.getCategories()

        if (remoteCategories is Result.Success) {
            categoriesLocalDataSource.deleteAllCategories()
            remoteCategories.data.forEach { category ->
                categoriesLocalDataSource.saveCategory(category)
            }
        } else if (remoteCategories is Result.Error) {
            throw remoteCategories.exception
        }
    }
}