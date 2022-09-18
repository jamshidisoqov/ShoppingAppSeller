package uz.gita.firebasesample.data.models.firebase

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import java.util.*

data class CategoryEntity(
    val id: String = UUID.randomUUID().toString(),
    val image: String,
    val name: String,
    val tags: String
) {
    private val gson = Gson()

    fun toCategoryData(): ProductCategoryData {
        val type = object : TypeToken<List<String>>() {}.type
        val tagsList = gson.fromJson<List<String>>(tags, type)
        return ProductCategoryData(id, name, image, tagsList)
    }
}
