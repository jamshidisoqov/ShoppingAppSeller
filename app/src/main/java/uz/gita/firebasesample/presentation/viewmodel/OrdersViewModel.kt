package uz.gita.firebasesample.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.firebasesample.data.models.local.OrdersData

// Created by Jamshid Isoqov an 9/18/2022
interface OrdersViewModel {

    val allOrders: Flow<List<OrdersData>>


    fun updateOrder(ordersData: OrdersData)

}