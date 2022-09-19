package uz.gita.firebasesample.presentation.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.firebasesample.data.models.local.OrdersData

// Created by Jamshid Isoqov an 9/18/2022
interface OrdersViewModel {

    val allOrders: Flow<List<OrdersData>>

    val progressLiveData: LiveData<Boolean>

    fun updateOrder(ordersData: OrdersData)

}