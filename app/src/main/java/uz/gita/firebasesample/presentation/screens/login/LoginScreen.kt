package uz.gita.firebasesample.presentation.screens.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.firebasesample.R
import uz.gita.firebasesample.databinding.ScreenLoginBinding
import uz.gita.firebasesample.presentation.viewmodel.LoginViewModel
import uz.gita.firebasesample.presentation.viewmodel.impl.LoginViewModelImpl

// Created by Jamshid Isoqov an 9/18/2022
@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {

    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()

    private val viewBinding: ScreenLoginBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnLogin.setOnClickListener {
            viewModel.login(
                viewBinding.inputLogin.text.toString(),
                viewBinding.inputPassword.text.toString()
            )
        }

        lifecycleScope.launch {
            viewModel.errorFlow.collectLatest {
                Snackbar.make(
                    viewBinding.btnLogin,
                    "Login or Password incorrect",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launch {
            viewModel.progressLiveData.collectLatest {
                if (it) {
                    viewBinding.progres.visibility = View.VISIBLE
                } else {
                    viewBinding.progres.visibility = View.GONE
                }
            }
        }
    }
}