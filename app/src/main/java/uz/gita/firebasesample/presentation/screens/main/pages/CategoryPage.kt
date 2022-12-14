package uz.gita.firebasesample.presentation.screens.main.pages

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.firebasesample.R
import uz.gita.firebasesample.databinding.PageCategoriesBinding
import uz.gita.firebasesample.presentation.screens.adapter.CategoryAdapter
import uz.gita.firebasesample.presentation.viewmodel.CategoriesViewModel
import uz.gita.firebasesample.presentation.viewmodel.impl.CategoriesViewModelImpl

// Created by Jamshid Isoqov an 9/18/2022
@AndroidEntryPoint
class CategoryPage : Fragment(R.layout.page_categories) {

    private val viewBinding: PageCategoriesBinding by viewBinding(PageCategoriesBinding::bind)

    private val viewModel: CategoriesViewModel by viewModels<CategoriesViewModelImpl>()

    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.update()
        viewBinding.categoryList.adapter = adapter

        viewModel.categoryListLiveData.observe(viewLifecycleOwner) {
            Log.d("TTT", "onViewCreated:${it.size}")
            adapter.submit(it)
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner){
            if (it)
            viewBinding.progressBar.visibility = View.VISIBLE
            else viewBinding.progressBar.visibility = View.INVISIBLE
        }

        adapter.setOnClickItemListener {
            viewModel.openProductScreen(it)
        }

        viewBinding.addBtn.setOnClickListener {
            viewModel.openAddCategory()
        }


    }

}