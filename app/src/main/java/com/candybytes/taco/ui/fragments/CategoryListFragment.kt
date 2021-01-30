package com.candybytes.taco.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.candybytes.taco.Application
import com.candybytes.taco.databinding.FragmentCategoryListBinding
import com.candybytes.taco.ui.vm.CategoriesViewModel
import com.candybytes.taco.ui.vm.CategoryViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    private val viewModel by viewModels<CategoriesViewModel> {
        CategoryViewModelFactory(
            (requireContext().applicationContext as Application).tac,
            (requireContext().applicationContext as Application).categoriesRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCategoryListBinding.inflate(layoutInflater, container, false).apply {
            viewModel = this@CategoryListFragment.viewModel
            lifecycleOwner = this@CategoryListFragment
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
