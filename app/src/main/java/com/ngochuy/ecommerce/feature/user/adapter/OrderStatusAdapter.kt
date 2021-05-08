package com.ngochuy.ecommerce.feature.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.OrderStatus
import com.ngochuy.ecommerce.databinding.ItemOrderStatusBinding

class OrderStatusAdapter(
    private var onOrderStatusClick: (orderStatus: OrderStatus) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<OrderStatus> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        OrderStatusViewHolder(
            ItemOrderStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listProductSale.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as OrderStatusViewHolder).bind(listProductSale[position])
    }

    fun setOrderStatusList(list: List<OrderStatus>) {
        listProductSale.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class OrderStatusViewHolder(
        private val binding: ItemOrderStatusBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderStatus) {
            binding.apply {
                orderStatus = item
                itemOrderStatus.setOnClickListener { onOrderStatusClick(item) }
            }
        }
    }
}