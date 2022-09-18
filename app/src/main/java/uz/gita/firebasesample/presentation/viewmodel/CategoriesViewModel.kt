package uz.gita.firebasesample.presentation.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.firebasesample.data.models.local.ProductCategoryData

// Created by Jamshid Isoqov an 9/18/2022
interface CategoriesViewModel {

    val categoryListLiveData: LiveData<List<ProductCategoryData>>

    fun openAddCategory()

    fun update()

    fun openProductScreen(productCategoryData: ProductCategoryData)
}