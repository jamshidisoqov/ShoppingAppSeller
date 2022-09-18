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
import uz.gita.firebasesample.presentation.screens.main.pages.CategoryPageDirections
import uz.gita.firebasesample.presentation.viewmodel.CategoriesViewModel
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

class CategoriesViewModelImpl @Inject constructor(
    repository: Repository
) : CategoriesViewModel, ViewModel(){

}