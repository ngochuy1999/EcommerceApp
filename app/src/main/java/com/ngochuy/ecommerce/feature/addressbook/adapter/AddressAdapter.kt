package com.ngochuy.ecommerce.feature.addressbook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.AddressType
import com.ngochuy.ecommerce.data.ShoppingAddress
import com.ngochuy.ecommerce.databinding.ItemAddressBinding

class AddressAdapter(private var onAddressClick: (address: ShoppingAddress, type: AddressType) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var addressList = arrayListOf<ShoppingAddress>()

    var currentPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemAddressBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_address,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.bind(addressList[position])
    }

    fun submitList(list: ArrayList<ShoppingAddress>) {
        addressList.clear()
        addressList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int?) {
        position ?: return
        addressList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ItemViewHolder(val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context

        fun bind(itemAdd: ShoppingAddress) {
            binding.apply {
                item = itemAdd
                executePendingBindings()
                itemAddress.setOnClickListener { itemAdd.let { it1 -> onAddressClick(it1, AddressType.CLICK) } }
                ivClose.setOnClickListener { itemAdd.let { it1 -> onAddressClick(it1,AddressType.DEL) } }
            }
        }
    }
}