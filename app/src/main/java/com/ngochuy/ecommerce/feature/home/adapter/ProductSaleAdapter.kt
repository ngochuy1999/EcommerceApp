package com.ngochuy.ecommerce.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.ProductDetail
import com.ngochuy.ecommerce.databinding.ItemProductSaleBinding

class ProductSaleAdapter(
        private var onProductClick: (id:Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<ProductDetail> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            PlantViewHolder(
                    ItemProductSaleBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listProductSale.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlantViewHolder).bind(listProductSale[position])
    }

    fun setProductList(list: List<ProductDetail>) {
        listProductSale.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class PlantViewHolder(
            private val binding: ItemProductSaleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductDetail) {
            binding.apply {
                product = item
                executePendingBindings()
                itemProductSale.setOnClickListener { item.productId?.let { it1 -> onProductClick(it1) } }
            }
        }
    }
}