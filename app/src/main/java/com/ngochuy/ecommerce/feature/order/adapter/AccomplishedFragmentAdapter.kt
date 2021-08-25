package com.ngochuy.ecommerce.feature.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.CartType
import com.ngochuy.ecommerce.data.Invoice
import com.ngochuy.ecommerce.data.Product
import com.ngochuy.ecommerce.databinding.ItemAccomplishedBinding
import com.ngochuy.ecommerce.databinding.ItemProductCartBinding

class AccomplishedFragmentAdapter(
    private var onProductClick: (id:Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listProductSale: ArrayList<Invoice> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ProductCartViewHolder(
            ItemAccomplishedBinding.inflate(
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
        private val binding: ItemAccomplishedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemAc: Invoice) {
            binding.apply {
                item = itemAc
                executePendingBindings()
                itemAccomplishOrder.setOnClickListener { item?.invoiceId?.let { it ->
                    onProductClick(
                        it
                    )
                } }
            }
        }
    }
}