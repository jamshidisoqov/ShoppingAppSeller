package uz.gita.firebasesample.presentation.viewmodel.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import uz.gita.firebasesample.data.models.local.ProductData
import uz.gita.firebasesample.data.pref.MySharedPref
import uz.gita.firebasesample.navigation.Navigator
import uz.gita.firebasesample.presentation.screens.products.ProductsScreenDirections
import uz.gita.firebasesample.presentation.viewmodel.ProductsViewModel
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

@HiltViewModel
class ProductsViewModelImpl @Inject constructor(
    private val repository: Repository,
    private val navigator: Navigator,
    private val sharedPref: MySharedPref
) : ProductsViewModel, ViewModel() {

    override val productListLiveData = MutableLiveData<List<ProductData>>()

    override val progressLiveData = MutableLiveData<Boolean>()


    override fun getProductsByCategoryId(productCategoryData: ProductCategoryData) {
        progressLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProductsByCategory(productCategoryData).onEach {
                progressLiveData.postValue(false)
                productListLiveData.postValue(it)
            }.collect()
        }
    }

    override fun openProductDetailsScreen(productData: ProductData) {
        viewModelScope.launch {
            navigator.navigateTo(
                ProductsScreenDirections.actionProductsScreenToProductDetailsScreen(
                    productData
                )
            )
        }

    }

    override fun openAddProductScreen(productCategoryData: ProductCategoryData) {
        viewModelScope.launch {
            navigator.navigateTo(
                ProductsScreenDirections.actionProductsScreenToAddProduct(
                    productCategoryData
                )
            )
        }
    }

    override fun searchProduct(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchProducts(query, sharedPref.getStoreId()).collect {
                productListLiveData.postValue(it)
            }
        }
    }


}