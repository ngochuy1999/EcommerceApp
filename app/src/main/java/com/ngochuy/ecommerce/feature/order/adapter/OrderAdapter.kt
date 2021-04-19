package com.ngochuy.ecommerce.feature.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngochuy.ecommerce.data.Order
import com.ngochuy.ecommerce.data.OrderStatus
import kotlinx.android.synthetic.main.item_order.view.*
import com.ngochuy.ecommerce.R

class OrderAdapter(
    private val onItemClick: (order: Order) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<Order>? = arrayListOf()
    private var listStatus: ArrayList<OrderStatus>? = arrayListOf()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_order, viewGroup, false);
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items?.get(position)?.let { (holder as OrderViewHolder).bind(it) }
    }

    fun setOrderList(list: List<Order>) {
        items?.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    private fun setOrderStatus(view: TextView, statusID: Int?) {
        if (listStatus != null)
            for (status in listStatus!!) {
                if (status.id == statusID) {
                    view.text = status.statusName
                    break;
                }
            }
    }

    fun setOrderStatusList(list: List<OrderStatus>) {
        listStatus?.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(order: Order) {
            // get name in list order detail to create string name include anothor names which have the same order id
            itemView.tv_name_item_order.text = order.name

            // set order id
            itemView.tv_item_order_detail_id.text = order.id.toString()
            // set order date
            itemView.tv_item_order_detail_date.text = order.buyDate
            // set status for order
/*
            var status = ""
            // have received
            when (order.statusID) {
                1 -> {
                    status = itemView.context.getString(R.string.status_1)
                    itemView.iv_order_ic_status.setImageResource(R.drawable.ic_circle_arrow);
                }
                // shipping
                2 -> {
                    status = itemView.context.getString(R.string.status_2)
                    itemView.iv_order_ic_status.setImageResource(R.drawable.ic_shipping);
                }
                // order success
                3 -> {
                    status = itemView.context.getString(R.string.status_3)
                    itemView.iv_order_ic_status.setImageResource(R.drawable.ic_success);
                }
                // order cancel
                4 -> {
                    status = itemView.context.getString(R.string.status_4)
                    itemView.iv_order_ic_status.setImageResource(R.drawable.ic_error);
                }
            }
            itemView.tv_item_order_status.text = status
*/
            setOrderStatus(itemView.tv_item_order_status, order.statusID)
            itemView.ll_item_order.setOnClickListener { onItemClick(order) }
        }

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
}