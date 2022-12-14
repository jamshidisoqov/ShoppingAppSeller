package uz.gita.firebasesample.data

import com.google.firebase.firestore.DocumentSnapshot
import uz.gita.firebasesample.data.models.firebase.CategoryEntity
import uz.gita.firebasesample.data.models.firebase.OrderEntity
import uz.gita.firebasesample.data.models.firebase.ProductEntity
import uz.gita.firebasesample.data.models.firebase.StoreEntity


object Mapper {

    fun DocumentSnapshot.toStore() = StoreEntity(
        id = this["id"].toString(),
        login = this["login"].toString(),
        storeName = this["name"].toString(),
        password = this["password"].toString(),
    )


    fun DocumentSnapshot.toCategory() = CategoryEntity(
        id = this["id"].toString(),
        image = this["image"].toString(),
        name = this["name"].toString(),
        tags = this["tags"].toString()
    )

    fun DocumentSnapshot.toOrder() = OrderEntity(
        id = this["id"].toString(),
        clientId = this["clientId"].toString(),
        details = this["details"].toString(),
        status = this["status"].toString()
    )

    fun DocumentSnapshot.toProduct() = ProductEntity(
        id = this["id"].toString(),
        name = this["name"].toString(),
        desc = this["description"].toString(),
        categoryId = this["categoryId"].toString(),
        photos = this["photos"].toString(),
        cell = this["cell"].toString(),
        storeId = this["storeId"].toString(),
        attr = this["attr"].toString()
    )


}