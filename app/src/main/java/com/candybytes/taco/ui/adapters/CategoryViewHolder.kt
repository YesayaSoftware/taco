package com.candybytes.taco.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candybytes.taco.databinding.ItemCategoryBinding
import com.candybytes.taco.ui.vm.CategoriesViewModel
import com.candybytes.taco.vo.Category

class CategoryViewHolder private constructor(val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: CategoriesViewModel, item: Category) {
        binding.viewModel = viewModel
        binding.category = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent : ViewGroup) : CategoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)

            return CategoryViewHolder(binding)
        }
    }
}