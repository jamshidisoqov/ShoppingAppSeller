package uz.gita.firebasesample.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.firebasesample.data.models.local.ProductData
import java.net.URI

interface AddProductViewModel {


    //    var openImagePickerDialogLiveData: MutableLiveData<Unit>
    val backLiveData: LiveData<Unit>
    val messageLiveData: LiveData<String>

    fun addProduct(productData: ProductData)

    fun loadImage(uri: Uri)

}