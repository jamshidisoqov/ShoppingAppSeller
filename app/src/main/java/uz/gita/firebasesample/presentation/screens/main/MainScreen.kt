package uz.gita.firebasesample.presentation.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.firebasesample.R
import uz.gita.firebasesample.databinding.ScreenMainBinding
import uz.gita.firebasesample.presentation.screens.adapter.StorePagerAdapter

// Created by Jamshid Isoqov an 9/18/2022
@AndroidEntryPoint
class MainScreen :  Fragment(R.layout.screen_main){

    private var list = arrayListOf("Category","Orders")

    private val viewBinding:ScreenMainBinding by viewBinding(ScreenMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.viewPager.adapter = StorePagerAdapter(requireActivity())

        TabLayoutMediator(viewBinding.tabLayout,viewBinding.viewPager){tab,position ->
            tab.text ="${list[position]}"
        }.attach()
    }

}