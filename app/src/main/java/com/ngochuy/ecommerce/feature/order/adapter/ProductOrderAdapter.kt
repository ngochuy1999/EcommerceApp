package com.ngochuy.ecommerce.feature.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.InvoiceDetail
import com.ngochuy.ecommerce.databinding.ItemProductInvoiceDetailBinding

class ProductOrderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<InvoiceDetail> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemProductInvoiceDetailViewHolder(
                    ItemProductInvoiceDetailBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listProductSale.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemProductInvoiceDetailViewHolder).bind(listProductSale[position])
    }

    fun setProductList(list: List<InvoiceDetail>) {
        listProductSale.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class ItemProductInvoiceDetailViewHolder(
            private val binding: ItemProductInvoiceDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemInvocie: InvoiceDetail) {
            binding.apply {
                item = itemInvocie
                executePendingBindings()
            }
        }
    }
}