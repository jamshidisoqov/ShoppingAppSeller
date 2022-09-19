package uz.gita.firebasesample.presentation.viewmodel.impl

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import uz.gita.firebasesample.navigation.Navigator
import uz.gita.firebasesample.presentation.viewmodel.AddCategoryViewModel
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModelImpl @Inject constructor(
    private val repository: Repository
) : AddCategoryViewModel, ViewModel() {

    private lateinit var uri: Uri

    override val closeScreenLiveData = MutableLiveData<Unit>()

    override val progressLiveData = MutableLiveData<Boolean>()

    override fun uploadImage(uri: Uri) {
        this.uri = uri
    }

    override fun addCategory(productCategoryData: ProductCategoryData) {
        progressLiveData.value = true
        viewModelScope.launch {
            val imageUrl = repository.uploadImage(uri)
            repository.addCategory(productCategoryData.copy(imageUrl = imageUrl))
            closeScreenLiveData.value = Unit
            progressLiveData.value = false

        }
    }

}