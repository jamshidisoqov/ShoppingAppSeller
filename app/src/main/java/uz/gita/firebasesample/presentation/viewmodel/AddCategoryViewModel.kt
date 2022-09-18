package uz.gita.firebasesample.presentation.viewmodel

import android.net.Uri
import uz.gita.firebasesample.data.models.local.ProductCategoryData

// Created by Jamshid Isoqov an 9/18/2022
interface AddCategoryViewModel {

    fun uploadImage(uri: Uri)

    fun addCategory(productCategoryData: ProductCategoryData)

}