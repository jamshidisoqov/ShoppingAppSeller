package uz.gita.firebasesample.presentation.screens.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.firebasesample.presentation.screens.main.pages.CategoryPage
import uz.gita.firebasesample.presentation.screens.main.pages.OrdersPage

// Created by Jamshid Isoqov an 9/18/2022
class StorePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> {
                CategoryPage()
            }
            else -> {
                OrdersPage()
            }
        }

}