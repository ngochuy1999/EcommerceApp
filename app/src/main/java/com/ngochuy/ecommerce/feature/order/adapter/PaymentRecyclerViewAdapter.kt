package com.ngochuy.ecommerce.feature.order.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Invoice
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.databinding.ItemAccomplishedBinding
import com.ngochuy.ecommerce.databinding.ItemPaymentBinding


class PaymentRecyclerViewAdapter(
    private var onProductClick: (id:Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<Invoice> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ProductCartViewHolder(
            ItemPaymentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listProductSale.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductCartViewHolder).bind(listProductSale[position])
    }

    fun setProductList(list: List<Invoice>) {
        listProductSale.apply {
            clear()
            addAll(list)
        }

        notifyDataSetChanged()
    }

    inner class ProductCartViewHolder(
        private val binding: ItemPaymentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPay: Invoice) {
            binding.apply {
                item = itemPay
                executePendingBindings()
                itemPaymentOrder.setOnClickListener { item?.invoiceId?.let { it ->
                    onProductClick(
                        it
                    )
                } }
            }
        }
    }
}