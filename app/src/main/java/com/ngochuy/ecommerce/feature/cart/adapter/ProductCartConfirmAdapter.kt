package com.ngochuy.ecommerce.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.databinding.ItemProductConfirmBinding
import com.ngochuy.ecommerce.roomdb.ProductEntity

class ProductCartConfirmAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<ProductEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ProductViewHolder(
                    ItemProductConfirmBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listProductSale.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(listProductSale[position])
    }

    fun setProductList(list: List<ProductEntity>) {
        listProductSale.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(
            private val binding: ItemProductConfirmBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductEntity) {
            binding.apply {
                product = item
                executePendingBindings()
            }
        }
    }

}