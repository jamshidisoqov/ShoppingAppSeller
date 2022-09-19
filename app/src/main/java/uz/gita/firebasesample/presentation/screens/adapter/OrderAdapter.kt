package uz.gita.firebasesample.presentation.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.models.local.Order
import uz.gita.firebasesample.data.models.local.OrdersData
import uz.gita.firebasesample.databinding.ListItemOrdersBinding

// Created by Jamshid Isoqov an 9/18/2022
class OrderAdapter : ListAdapter<OrdersData, OrderAdapter.ViewHolder>(itemOrderCallback) {


    private var updateListener: ((OrdersData) -> Unit)? = null

    fun setUpdateListener(block: (OrdersData) -> Unit) {
        updateListener = block
    }

    inner class ViewHolder(private val binding: ListItemOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvOrderStatus.setOnClickListener {
                val data = getItem(absoluteAdapterPosition)
                if (data.status == "In Review") {
                    binding.tvOrderStatus.text = "Approved"
                    updateListener?.invoke(data.copy(status = "Approved"))
                } else {
                    binding.tvOrderStatus.text = "In Review"
                    updateListener?.invoke(data.copy(status = "In Review"))
                }
            }
        }

        fun onBind() {
            val data = getItem(absoluteAdapterPosition)
            binding.apply {
                tvUserName.text = "Zakaz${absoluteAdapterPosition + 1}"
                tvOrderStatus.text = data.status
                tvOrder.text = "Total amount:${calculateSumm(data.details)}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListItemOrdersBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_orders, parent, false)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()

    private fun calculateSumm(list: List<Order>): Int {
        var summ = 0
        list.forEach {
            summ += it.sell.toInt() * it.count
        }
        return summ
    }
}


private val itemOrderCallback = object : DiffUtil.ItemCallback<OrdersData>() {
    override fun areItemsTheSame(oldItem: OrdersData, newItem: OrdersData): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: OrdersData, newItem: OrdersData): Boolean =
        oldItem.clientId == newItem.clientId && oldItem.details == newItem.details && oldItem.status == newItem.status


}