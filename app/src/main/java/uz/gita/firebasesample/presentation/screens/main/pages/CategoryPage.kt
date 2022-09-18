package uz.gita.firebasesample.presentation.screens.main.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.firebasesample.R
import uz.gita.firebasesample.databinding.PageCategoriesBinding
import uz.gita.firebasesample.presentation.viewmodel.CategoriesViewModel
import uz.gita.firebasesample.presentation.viewmodel.impl.CategoriesViewModelImpl

// Created by Jamshid Isoqov an 9/18/2022
class CategoryPage : Fragment(R.layout.page_categories) {

    private val viewBinding:PageCategoriesBinding by viewBinding(PageCategoriesBinding::bind)
    private val viewModel:CategoriesViewModel by viewModels<CategoriesViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}