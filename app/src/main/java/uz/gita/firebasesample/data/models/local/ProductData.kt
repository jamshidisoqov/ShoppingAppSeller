package uz.gita.firebasesample.data.models.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.gita.firebasesample.data.models.firebase.ProductEntity
import java.io.Serializable

// Created by Jamshid Isoqov an 9/17/2022
data class ProductData(
    val id: String,
    val categoryId: String,
    val name: String,
    val description: String,
    val photos: String,
    val sell: String,
    val attrs: List<Pair<String, String>>,
    val storeId: String
) :Serializable{
    fun toProductEntity(): ProductEntity {
        val gson = Gson()
        val type = object : TypeToken<List<Pair<String, String>>>() {}.type
        val gsonString = gson.toJson(attrs, type)
        return ProductEntity(id, name, description, categoryId, photos, sell, storeId, gsonString)
    }
}
