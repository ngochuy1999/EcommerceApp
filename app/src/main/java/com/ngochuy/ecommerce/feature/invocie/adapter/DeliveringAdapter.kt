package com.ngochuy.ecommerce.feature.invocie.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ngochuy.ecommerce.data.Invoice
import com.ngochuy.ecommerce.databinding.ItemDeliveringBinding


class DeliveringAdapter(
    private var onProductClick: (id:Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<Invoice> = arrayListOf()

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

    fun setProductList(list: List<Invoice>) {
        listProductSale.apply {
            clear()
            addAll(list)
        }

        notifyDataSetChanged()
    }

    inner class ProductCartViewHolder(
        private val binding: ItemDeliveringBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemDe: Invoice) {
            binding.apply {
                item = itemDe
                executePendingBindings()
                itemDeliveryOrder.setOnClickListener { item?.invoiceId?.let { it ->
                    onProductClick(
                        it
                    )
                } }
            }
        }
    }
}