package uz.gita.firebasesample.data.models.firebase

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.gita.firebasesample.data.models.local.ProductData
import java.util.*

data class ProductEntity(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val desc: String,
    val categoryId: String,
    val photos: String,
    val cell: String,
    val storeId: String,
    val attr: String
) {
    private val gson = Gson()

    fun toProductData(): ProductData {
        val type = object : TypeToken<List<Pair<String, String>>>() {}.type
        val attrs = gson.fromJson<List<Pair<String, String>>>(attr, type)
        return ProductData(id, categoryId, name, desc, photos, cell, attrs, storeId)
    }
}