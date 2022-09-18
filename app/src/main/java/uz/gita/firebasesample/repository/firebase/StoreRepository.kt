package uz.gita.firebasesample.repository.firebase

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.gita.firebasesample.data.models.firebase.CategoryEntity
import uz.gita.firebasesample.data.models.firebase.OrderEntity
import uz.gita.firebasesample.data.models.firebase.ProductEntity


interface StoreRepository {

    suspend fun loginSore(login: String, password: String): Boolean

    fun getCategories(): Flow<List<CategoryEntity>>

    fun getProductsByCategory(categoryId: String): Flow<List<ProductEntity>>

    suspend fun updateOrders(orderEntity: OrderEntity)

    suspend fun addCategory(categoryEntity: CategoryEntity)

    suspend fun addProducts(productEntity: ProductEntity)

    fun getAllOrders(): Flow<List<OrderEntity>>

    suspend fun uploadImage(uri: Uri):String

}