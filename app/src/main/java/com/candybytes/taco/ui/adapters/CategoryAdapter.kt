package com.candybytes.taco.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.candybytes.taco.ui.vm.CategoriesViewModel
import com.candybytes.taco.vo.Category

class CategoryAdapter (private val viewModel: CategoriesViewModel) : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }
}