package com.ngochuy.ecommerce.feature.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.base.DynamicSearchAdapter
import com.ngochuy.ecommerce.data.ProductDetail
import com.ngochuy.ecommerce.databinding.ItemProductBinding

class ProductAdapter(
    private var listProductSale: MutableList<ProductDetail> = arrayListOf(),
    private var onProductClick: (id: Int) -> Unit
) : DynamicSearchAdapter<ProductDetail>(listProductSale) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            PlantViewHolder(
                    ItemProductBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listProductSale.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlantViewHolder).bind(listProductSale[position])
    }

    fun setProductList(list: ArrayList<ProductDetail>) {
        listProductSale.apply {
            clear()
            addAll(list)
            updateData(list)
            notifyDataSetChanged()
        }
    }

    fun addDataSearch(arr: MutableList<ProductDetail>) {
        listProductSale.apply {
            clear()
            addAll(arr)
            notifyDataSetChanged()
        }
    }

    fun removeAllData() {
        listProductSale.apply {
            clear()
            notifyDataSetChanged()
        }
    }

    inner class PlantViewHolder(
            private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductDetail) {
            binding.apply {
                product = item
                executePendingBindings()
                itemProduct.setOnClickListener { item.productId?.let { it1 -> onProductClick(it1) } }
            }
        }
    }
}