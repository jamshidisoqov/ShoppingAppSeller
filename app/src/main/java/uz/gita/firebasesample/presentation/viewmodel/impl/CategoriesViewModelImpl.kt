package uz.gita.firebasesample.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.firebasesample.presentation.viewmodel.CategoriesViewModel
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModelImpl @Inject constructor(
    repository: Repository
) : CategoriesViewModel, ViewModel(){

}