package uz.gita.firebasesample.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.firebasesample.presentation.viewmodel.AddCategoryViewModel
import uz.gita.firebasesample.repository.firebase.StoreRepositoryImpl
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModelImpl @Inject constructor(
    private val repository: Repository
) : AddCategoryViewModel, ViewModel() {

}