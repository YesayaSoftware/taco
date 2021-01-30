package com.candybytes.taco.ui.vm

import androidx.hilt.Assisted
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.data.repositories.CategoriesRepository

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(
    private val tacoService: TacoService,
    private val categoriesRepository: CategoriesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CategoriesViewModel(tacoService, categoriesRepository, savedStateHandle) as T)
}