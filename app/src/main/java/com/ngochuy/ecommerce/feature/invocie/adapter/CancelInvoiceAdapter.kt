package com.ngochuy.ecommerce.feature.invocie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.Invoice
import com.ngochuy.ecommerce.databinding.ItemCancelInvoiceBinding

class CancelInvoiceAdapter (
    private var onProductClick: (id:Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<Invoice> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ProductCartViewHolder(
            ItemCancelInvoiceBinding.inflate(
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
        private val binding: ItemCancelInvoiceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemAll : Invoice) {
            binding.apply {
                item = itemAll
                executePendingBindings()
                itemCancelInvoice.setOnClickListener { item?.invoiceId?.let { it1 ->
                    onProductClick(
                        it1
                    )
                } }
            }
        }
    }
}