package uz.gita.firebasesample.repository.local.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import uz.gita.firebasesample.data.models.local.OrdersData
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import uz.gita.firebasesample.data.models.local.ProductData
import uz.gita.firebasesample.repository.firebase.StoreRepository
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val storeRepository: StoreRepository
) : Repository {

    override suspend fun loginStore(login: String, password: String): Boolean =
        storeRepository.loginSore(login, password)

    override fun getCategories(): Flow<List<ProductCategoryData>> =
        storeRepository.getCategories().map { categories ->
            categories.map {
                it.toCategoryData()
            }
        }

    override fun getProductsByCategory(productCategoryData: ProductCategoryData): Flow<List<ProductData>> =
        storeRepository.getProductsByCategory(productCategoryData.id).map { products ->
            products.map {
                it.toProductData()
            }
        }

    override suspend fun updateOrders(ordersData: OrdersData) =
        storeRepository.updateOrders(ordersData.toOrderEntity())

    override suspend fun addProducts(productData: ProductData) =
        storeRepository.addProducts(productData.toProductEntity())

    override fun searchProducts(query: String,categoryId:String): Flow<List<ProductData>> =
        storeRepository.getProductsByCategory(categoryId).map { products ->
            products.map {
                it.toProductData()
            }
        }

    override fun getAllOrders(): Flow<List<OrdersData>> =
        storeRepository.getAllOrders().map { orders ->
            orders.map {
                it.toOrderData()
            }
        }

    override fun uploadImage() {
        TODO("Not yet implemented")
    }
}