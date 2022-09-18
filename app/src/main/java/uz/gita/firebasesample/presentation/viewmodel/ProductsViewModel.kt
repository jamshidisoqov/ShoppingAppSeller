package uz.gita.firebasesample.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import uz.gita.firebasesample.data.models.local.ProductData

// Created by Jamshid Isoqov an 9/18/2022
interface ProductsViewModel {

    val productListLiveData:LiveData<List<ProductData>>

    fun getProductsByCategoryId(productCategoryData: ProductCategoryData)

    fun openProductDetailsScreen(productData: ProductData)

    fun openAddProductScreen()

    fun searchProduct(query:String)
}