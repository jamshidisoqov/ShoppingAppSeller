package uz.gita.firebasesample.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.firebasesample.data.models.local.OrdersData
import uz.gita.firebasesample.presentation.viewmodel.OrdersViewModel
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

@HiltViewModel
class OrdersViewModelImpl @Inject constructor(
    private val repository: Repository
) : OrdersViewModel, ViewModel() {

    override val allOrders = MutableSharedFlow<List<OrdersData>>()

    override val progressLiveData = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            progressLiveData.postValue(true)
            repository.getAllOrders().collectLatest {
                allOrders.emit(it)
                progressLiveData.postValue(false)
            }
        }
    }

    override fun updateOrder(ordersData: OrdersData) {
        viewModelScope.launch {
            repository.updateOrders(ordersData)
        }
    }

}