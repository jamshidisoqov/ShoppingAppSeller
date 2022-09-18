package uz.gita.firebasesample.data.models.firebase

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.gita.firebasesample.data.models.local.Order
import uz.gita.firebasesample.data.models.local.OrdersData
import java.util.*

data class OrderEntity(
    val id: String = UUID.randomUUID().toString(),
    var clientId: String,
    var details: String,
    var status: String,
) {
    fun toOrderData(): OrdersData {
        val gson = Gson()
        val type = object : TypeToken<List<Order>>() {}.type
        val list = gson.fromJson<List<Order>>(details, type)
        return OrdersData(id, clientId, status, list)
    }
}
