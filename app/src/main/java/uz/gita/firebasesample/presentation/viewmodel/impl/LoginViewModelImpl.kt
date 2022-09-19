package uz.gita.firebasesample.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.firebasesample.navigation.Navigator
import uz.gita.firebasesample.presentation.screens.login.LoginScreenDirections
import uz.gita.firebasesample.presentation.viewmodel.LoginViewModel
import uz.gita.firebasesample.repository.local.Repository
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val repository: Repository,
    private val navigator: Navigator
) : LoginViewModel, ViewModel() {

    override val errorFlow = MutableSharedFlow<Unit>()
    override val progressLiveData = MutableStateFlow<Boolean>(false)

    override fun login(login: String, password: String) {
        viewModelScope.launch {
            progressLiveData.value = true
            if (repository.loginStore(login, password)) {
                progressLiveData.value = false
                navigator.navigateTo(LoginScreenDirections.actionLoginScreenToMainScreen())
            } else {
                progressLiveData.value = true
                errorFlow.emit(Unit)
            }
        }
    }
}