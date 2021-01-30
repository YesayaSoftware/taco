package com.candybytes.taco

import android.app.Application
import com.candybytes.taco.data.repositories.CategoriesRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

@HiltAndroidApp
class Application : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    val categoriesRepository: CategoriesRepository
        get() = ServiceLocator.provideTasksRepository(this)

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
