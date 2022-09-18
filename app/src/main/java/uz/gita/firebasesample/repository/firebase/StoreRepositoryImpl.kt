package uz.gita.firebasesample.repository.firebase

import android.net.Uri
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.firebasesample.data.Mapper
import uz.gita.firebasesample.data.Mapper.toCategory
import uz.gita.firebasesample.data.Mapper.toOrder
import uz.gita.firebasesample.data.Mapper.toProduct
import uz.gita.firebasesample.data.models.firebase.CategoryEntity
import uz.gita.firebasesample.data.models.firebase.OrderEntity
import uz.gita.firebasesample.data.models.firebase.ProductEntity
import uz.gita.firebasesample.data.models.firebase.StoreEntity
import uz.gita.firebasesample.data.pref.MySharedPref
import java.util.*
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val mySharedPref: MySharedPref
) : StoreRepository {

    private val db = Firebase.firestore

    override suspend fun loginSore(login: String, password: String): Boolean {
        val list = ArrayList<StoreEntity>()
        db.collection("admin").get()
            .addOnSuccessListener {
                val ls = it.documents.map { item ->
                    Mapper.run {
                        item.toStore()
                    }
                }
                list.addAll(ls)
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
        val list = ArrayList<CategoryEntity>()
        db.collection("categories").get()
            .addOnSuccessListener {
                val ls = it.documents.map { item ->
                    item.toCategory()
                }
                list.addAll(ls)
            }

        emit(list)
    }

    override fun getProductsByCategory(categoryId: String): Flow<List<ProductEntity>> = flow {
        val list = ArrayList<ProductEntity>()
        db.collection("product").get()
            .addOnSuccessListener {
                val ls = it.map { item ->
                    item.toProduct()
                }.filter { product ->
                    product.categoryId == categoryId && product.storeId == mySharedPref.getStoreId()
                }
                list.addAll(ls)
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
        val list = ArrayList<OrderEntity>()
        db.collection("orders").get()
            .addOnSuccessListener {
                val ls = it.map { item ->
                    item.toOrder()
                }
                list.addAll(ls)
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