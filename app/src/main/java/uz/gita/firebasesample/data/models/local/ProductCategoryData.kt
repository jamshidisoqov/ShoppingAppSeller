package uz.gita.firebasesample.data.models.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.gita.firebasesample.data.models.firebase.CategoryEntity
import java.io.Serializable
import java.util.*

// Created by Jamshid Isoqov an 9/17/2022

data class ProductCategoryData(
    val id: String= UUID.randomUUID().toString(),
    val name: String,
    val imageUrl: String = "",
    val tags: List<String>
) : Serializable {
    fun toCategoryEntity(): CategoryEntity {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        val gsonString = gson.toJson(tags, type)
        return CategoryEntity(id, imageUrl, name, gsonString)
    }
}
