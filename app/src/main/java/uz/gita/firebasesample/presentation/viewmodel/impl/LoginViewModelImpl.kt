package uz.gita.firebasesample.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.firebasesample.data.pref.MySharedPref
import uz.gita.firebasesample.navigation.Navigator
import uz.gita.firebasesample.presentation.screens.login.LoginScreen
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

    override fun login(login: String, password: String) {
        viewModelScope.launch {
            if (repository.loginStore(login, password)) {
                navigator.navigateTo(LoginScreenDirections.actionLoginScreenToMainScreen())
            }else{
                errorFlow.emit(Unit)
            }
        }
    }
}