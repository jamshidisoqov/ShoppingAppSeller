package uz.gita.firebasesample.presentation.viewmodel.impl

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.firebasesample.data.pref.MySharedPref
import uz.gita.firebasesample.navigation.Navigator
import uz.gita.firebasesample.presentation.screens.splash.SplashScreenDirections
import uz.gita.firebasesample.presentation.viewmodel.SplashScreenViewModel
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModelImpl @Inject constructor(
    private val sharedPreferences: MySharedPref,
    private val navigator: Navigator
) : SplashScreenViewModel, ViewModel() {

    override fun openNewScreen() {
        viewModelScope.launch {
            delay(2000)
            if (sharedPreferences.getStoreId() == "") {
                navigator.navigateTo(SplashScreenDirections.actionSplashScreenToLoginScreen())
            } else {
                navigator.navigateTo(SplashScreenDirections.actionSplashScreenToMainScreen())
            }
        }
    }
}