package uz.gita.firebasesample.presentation.screens.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.pref.MySharedPref
import uz.gita.firebasesample.data.pref.impl.MySharedPrefImpl
import uz.gita.firebasesample.databinding.ScreenSplashBinding
import uz.gita.firebasesample.presentation.viewmodel.SplashScreenViewModel
import uz.gita.firebasesample.presentation.viewmodel.impl.SplashScreenViewModelImpl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val binding: ScreenSplashBinding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashScreenViewModel by viewModels<SplashScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.openNewScreen()

        memoryAnimation(binding.memory)
        gameAnimation(binding.game)

    }

    private fun memoryAnimation(view: View) {

        val rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        val transitionY =
            ObjectAnimator.ofFloat(
                view,
                "translationY",
                view.y,
                359f
            )

        AnimatorSet().apply {
            play(transitionY).with(rotation)
            duration = 1900
            start()
        }
    }

    private fun gameAnimation(view: View) {

        val rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, -360f)
        val transitionY =
            ObjectAnimator.ofFloat(
                view,
                "translationY",
                view.y,
                -359f
            )

        AnimatorSet().apply {
            play(transitionY).with(rotation)
            duration = 1900
            start()
        }
    }
}