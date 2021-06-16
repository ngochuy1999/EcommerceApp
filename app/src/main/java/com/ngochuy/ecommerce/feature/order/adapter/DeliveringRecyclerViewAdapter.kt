package com.ngochuy.ecommerce.feature.order.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.databinding.ItemDeliveringBinding


class DeliveringRecyclerViewAdapter(
    private var onProductClick: (id:Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<Product> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ProductCartViewHolder(
            ItemDeliveringBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listProductSale.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductCartViewHolder).bind(listProductSale[position])
    }

    fun setProductList(list: List<Product>) {
        listProductSale.apply {
            clear()
            addAll(list)
        }

        notifyDataSetChanged()
    }

    inner class ProductCartViewHolder(
        private val binding: ItemDeliveringBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.apply {
                product = item
                executePendingBindings()
                itemAccomplishOrder.setOnClickListener { item?.let { it1 -> it1.id?.let { it2 ->
                    onProductClick(
                        it2
                    )
                } }
                }
            }
        }
    }
}