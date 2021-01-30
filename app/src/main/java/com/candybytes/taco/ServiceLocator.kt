package com.candybytes.taco

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.data.db.FoodDb
import com.candybytes.taco.data.repositories.CategoriesRepository
import com.candybytes.taco.data.repositories.CategoriesRepositoryImpl
import com.candybytes.taco.data.sources.CategoryDataSource
import com.candybytes.taco.data.sources.local.CategoriesLocalDataSource
import com.candybytes.taco.data.sources.remote.CategoriesRemoteDataSource
import kotlinx.coroutines.runBlocking

/**
 * A Service Locator for the [CategoriesRepository]. This is the prod version, with a
 * the "real" [CategoriesRemoteDataSource].
 */
object ServiceLocator {
    private val lock = Any()
    private var database: FoodDb? = null
    private var remoteDataSource : CategoriesRemoteDataSource? = null
    @Volatile
    var categoriesRepository: CategoriesRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): CategoriesRepository {
        synchronized(this) {
            return categoriesRepository ?: createCategoriesRepository(context)
        }
    }

    private fun createCategoriesRepository(context: Context): CategoriesRepository {
        val newRepo = CategoriesRepositoryImpl(CategoriesRemoteDataSource, createTaskLocalDataSource(context))
        categoriesRepository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): CategoryDataSource {
        val database = database ?: createDataBase(context)
        return CategoriesLocalDataSource(database.categoriesDao())
    }
}