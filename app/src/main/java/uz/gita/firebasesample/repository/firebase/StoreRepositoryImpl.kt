package uz.gita.firebasesample.repository.firebase

import android.net.Uri
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import uz.gita.firebasesample.data.Mapper.toCategory
import uz.gita.firebasesample.data.Mapper.toOrder
import uz.gita.firebasesample.data.Mapper.toProduct
import uz.gita.firebasesample.data.Mapper.toStore
import uz.gita.firebasesample.data.models.firebase.CategoryEntity
import uz.gita.firebasesample.data.models.firebase.OrderEntity
import uz.gita.firebasesample.data.models.firebase.ProductEntity
import uz.gita.firebasesample.data.pref.MySharedPref
import java.util.*
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val mySharedPref: MySharedPref
) : StoreRepository {

    private val db = Firebase.firestore

    override suspend fun loginSore(login: String, password: String): Boolean {
        val list = db.collection("admin").get().await().documents
            .map {
                it.toStore()
            }
        list.filter {
            it.login == login && it.password == password
        }.forEach {
            mySharedPref.setStoreId(it.id)
            return true
        }
        return false
    }

    override fun getCategories(): Flow<List<CategoryEntity>> = flow {
        val list = db.collection("categories").get().await().documents
            .map {
                it.toCategory()
            }
        emit(list)
    }

    override fun getProductsByCategory(categoryId: String): Flow<List<ProductEntity>> = flow {
        val list = db.collection("product").get().await().documents
            .map { item ->
                item.toProduct()
            }.filter { product ->
                product.categoryId == categoryId && product.storeId == mySharedPref.getStoreId()
            }

        emit(list)
    }

    override suspend fun updateOrders(orderEntity: OrderEntity) {
        db.collection("orders").document(orderEntity.id).set(orderEntity)
    }

    override suspend fun addCategory(categoryEntity: CategoryEntity) {
        db.collection("categories").document(categoryEntity.id).set(categoryEntity)
    }

    override suspend fun addProducts(productEntity: ProductEntity) {
        db.collection("product").document(productEntity.id).set(productEntity)
    }


    override fun getAllOrders(): Flow<List<OrderEntity>> = flow {
        val list = db.collection("orders")
            .get()
            .await()
            .documents
            .map { item ->
                item.toOrder()
            }

        emit(list)
    }

    override suspend fun uploadImage(uri: Uri): String {

        val fileName = UUID.randomUUID().toString() + ".jpg"

        var imageUrl = ""

        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

        refStorage.putFile(uri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
            }
        }

            .addOnFailureListener { e ->
                print(e.message)
            }

        return imageUrl
    }


}