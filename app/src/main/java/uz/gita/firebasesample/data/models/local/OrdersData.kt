package uz.gita.firebasesample.data.models.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.gita.firebasesample.data.models.firebase.OrderEntity

// Created by Jamshid Isoqov an 9/17/2022
data class OrdersData(
    val id: String,
    val clientId: String,
    val status: String,
    val details: List<Order>
) {
    fun toOrderEntity(): OrderEntity {
        val gson = Gson()
        val type = object : TypeToken<List<Order>>() {}.type
        val gsonString = gson.toJson(details, type)
        return OrderEntity(
            id = id,
            clientId = clientId,
            status = status,
            details = gsonString
        )
    }
}
