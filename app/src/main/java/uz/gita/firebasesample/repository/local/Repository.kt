package uz.gita.firebasesample.repository.local

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.gita.firebasesample.data.models.firebase.OrderEntity
import uz.gita.firebasesample.data.models.local.*

// Created by Jamshid Isoqov an 9/17/2022
interface Repository {

    suspend fun loginStore(login:String,password:String):Boolean

    fun getCategories(): Flow<List<ProductCategoryData>>

    fun getProductsByCategory(productCategoryData: ProductCategoryData): Flow<List<ProductData>>

    suspend fun updateOrders(ordersData: OrdersData)

    suspend fun addProducts(productData: ProductData)

    fun searchProducts(query: String,categoryId:String): Flow<List<ProductData>>

    fun getAllOrders(): Flow<List<OrdersData>>

    suspend fun uploadImage(uri:Uri):String
}