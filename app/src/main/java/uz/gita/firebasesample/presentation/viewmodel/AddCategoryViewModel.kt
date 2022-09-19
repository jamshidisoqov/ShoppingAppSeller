package uz.gita.firebasesample.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import uz.gita.firebasesample.data.models.local.ProductCategoryData

// Created by Jamshid Isoqov an 9/18/2022
interface AddCategoryViewModel {

    val closeScreenLiveData: LiveData<Unit>

    val progressLiveData:LiveData<Boolean>

    fun uploadImage(uri: Uri)

    fun addCategory(productCategoryData: ProductCategoryData)

}