package com.ngochuy.ecommerce.feature.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.Category
import com.ngochuy.ecommerce.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var onCategoryClick: (id:String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listCategory: ArrayList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PlantViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listCategory.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlantViewHolder).bind(listCategory[position])
    }

    fun setListCategory(list: ArrayList<String>) {
        listCategory.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class PlantViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.apply {
                category = item
                executePendingBindings()
                itemCategory.setOnClickListener { item?.let { it1 -> onCategoryClick(it1) } }
            }
        }
    }
}