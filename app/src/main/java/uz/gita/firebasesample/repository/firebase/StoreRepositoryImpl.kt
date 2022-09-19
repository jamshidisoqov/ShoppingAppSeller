package uz.gita.firebasesample.repository.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override fun getCategories(): Flow<List<CategoryEntity>> = callbackFlow {
        val subscriber = db.collection("categories").addSnapshotListener { value, error ->
            val list = value?.documents?.map {
                it.toCategory()
            }
            trySend(list ?: emptyList())
        }
        awaitClose {
            subscriber.remove()
        }
    }

    override fun getProductsByCategory(categoryId: String): Flow<List<ProductEntity>> =
        callbackFlow {
            val list = db.collection("product").addSnapshotListener { value, error ->
                val data = value?.documents?.map { item ->
                    item.toProduct()
                }?.filter { product ->
                    product.categoryId == categoryId
                }
                trySend(data ?: emptyList())
            }
            awaitClose {
                list.remove()
            }
        }

    override suspend fun updateOrders(orderEntity: OrderEntity) {
        db.collection("orders").document(orderEntity.id).update("status", orderEntity.status)
    }

    override suspend fun addCategory(categoryEntity: CategoryEntity) {
        db.collection("categories").document(categoryEntity.id).set(categoryEntity)
    }

    override suspend fun addProducts(productEntity: ProductEntity) {
        db.collection("product").document(productEntity.id).set(productEntity)
    }


    override fun getAllOrders(): Flow<List<OrderEntity>> = callbackFlow {
        val list = db.collection("orders").addSnapshotListener { value, error ->
            val data = value?.map { it.toOrder() }
            trySend(data ?: emptyList())
        }
        awaitClose {
            list.remove()
        }
    }

    override suspend fun uploadImage(uri: Uri): String {

        val fileName = UUID.randomUUID().toString() + ".jpg"


        val deferred = CompletableDeferred<String>()
        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

        refStorage.putFile(uri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                val imageUrl = it.toString()
                Log.d("TTT", "imageUrl=$imageUrl")
                deferred.complete(imageUrl)
            }
        }
            .addOnFailureListener { e ->
                deferred.completeExceptionally(e)
            }

        return deferred.await()
    }
}