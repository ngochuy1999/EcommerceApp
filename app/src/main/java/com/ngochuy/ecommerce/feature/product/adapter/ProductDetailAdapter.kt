package com.ngochuy.ecommerce.feature.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.databinding.ItemCategoryBinding
import com.ngochuy.ecommerce.databinding.ItemProductConfigurationBinding


class ProductDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductDetail: ArrayList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            PlantViewHolder(
                    ItemProductConfigurationBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listProductDetail.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlantViewHolder).bind(listProductDetail[position])
    }

    fun setListProductDetail(list: ArrayList<String>) {
        listProductDetail.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class PlantViewHolder(
            private val binding: ItemProductConfigurationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.apply {
                binding.details = item
                executePendingBindings()
            }
        }
    }
}