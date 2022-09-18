package uz.gita.firebasesample.presentation.viewmodel.impl

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import uz.gita.firebasesample.data.models.local.ProductData
import uz.gita.firebasesample.presentation.viewmodel.AddProductViewModel
import uz.gita.firebasesample.repository.local.impl.RepositoryImpl
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class AddProductViewModelImpl @Inject constructor(private val repositoryImpl: RepositoryImpl) :
    AddProductViewModel, ViewModel() {

//    override var openImagePickerDialogLiveData: MutableLiveData<Unit> = MutableLiveData()

    override var backLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val messageLiveData: MutableLiveData<String> = MutableLiveData()

    override fun addProduct(productData: ProductData, uri: Uri) {


        if (productData.name.isNotEmpty() && productData.description.isNotEmpty() && productData.sell.isNotEmpty()) {

            if (productData.photos.isNotEmpty()) {

                var url = ""
                viewModelScope.launch(Dispatchers.IO) {
                    url = repositoryImpl.uploadImage(uri)
                    repositoryImpl.addProducts(
                        ProductData(
                            productData.id,
                            productData.categoryId,
                            productData.name,
                            productData.description,
                            url,
                            productData.sell,
                            productData.attrs,
                            productData.storeId
                        )
                    )
                    backLiveData.value = Unit
                    messageLiveData.value = "Saqlandi"
                }
            } else {
                messageLiveData.value = "Rasm tanlang"
            }
        } else {
            messageLiveData.value = "Maydonlar bo'sh"
        }
    }
}