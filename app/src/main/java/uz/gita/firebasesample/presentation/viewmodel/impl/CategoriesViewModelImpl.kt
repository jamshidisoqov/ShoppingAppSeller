package uz.gita.firebasesample.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import uz.gita.firebasesample.data.pref.MySharedPref
import uz.gita.firebasesample.navigation.Navigator
import uz.gita.firebasesample.presentation.screens.main.MainScreenDirections
import uz.gita.firebasesample.presentation.viewmodel.CategoriesViewModel
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModelImpl @Inject constructor(
    private val repository: Repository,
    private val sharedPref: MySharedPref,
    private val navigator: Navigator
) : CategoriesViewModel, ViewModel(){

    override val categoryListLiveData = MutableLiveData<List<ProductCategoryData>>()

    override fun openAddCategory() {
        viewModelScope.launch {
            navigator.navigateTo(MainScreenDirections.actionMainScreenToAddCategory())
        }
    }

    override fun openProductScreen(productCategoryData: ProductCategoryData) {
        viewModelScope.launch {
            navigator.navigateTo(MainScreenDirections.actionMainScreenToProductsScreen(productCategoryData))
        }

    }

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getCategories().collect{
                categoryListLiveData.postValue(it)
            }
        }
    }
}