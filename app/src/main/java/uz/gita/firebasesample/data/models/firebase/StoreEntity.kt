package uz.gita.firebasesample.data.models.firebase

import java.util.*

data class StoreEntity(
    val id: String = UUID.randomUUID().toString(),
    val login: String,
    val storeName: String,
    val password: String
)