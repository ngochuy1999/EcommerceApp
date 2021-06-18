package com.ngochuy.ecommerce.feature.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.databinding.ItemProductOrderBinding

class ProductOrderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: MutableList<Product> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ProductConfirmViewHolder(
                    ItemProductOrderBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listProductSale.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductConfirmViewHolder).bind(listProductSale[position])
    }

    fun setProductList(list: Product) {
        listProductSale.apply {
            clear()
            add(list)
            notifyDataSetChanged()
        }
    }

    inner class ProductConfirmViewHolder(
            private val binding: ItemProductOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.apply {
                product = item
                executePendingBindings()
            }
        }
    }
}