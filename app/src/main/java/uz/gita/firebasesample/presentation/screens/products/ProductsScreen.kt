package uz.gita.firebasesample.presentation.screens.products

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.client.adapters.ProductAdapter
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import uz.gita.firebasesample.data.models.local.ProductData
import uz.gita.firebasesample.databinding.ScreenProductsBinding
import uz.gita.firebasesample.presentation.viewmodel.ProductsViewModel
import uz.gita.firebasesample.presentation.viewmodel.impl.ProductsViewModelImpl

// Created by Jamshid Isoqov an 9/18/2022
@AndroidEntryPoint
class ProductsScreen : Fragment(R.layout.screen_products) {

    private val adapter: ProductAdapter by lazy { ProductAdapter() }

    private val viewModel: ProductsViewModel by viewModels<ProductsViewModelImpl>()

    private val viewBinding: ScreenProductsBinding by viewBinding(ScreenProductsBinding::bind)

    private val args: ProductsScreenArgs by navArgs()
    private lateinit var categoryData: ProductCategoryData


    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getProductsByCategoryId(args.category)

        viewModel.productListLiveData.observe(this, listObserver)

        viewBinding.productList.adapter = adapter

        adapter.setItemOnClickListener {
            viewModel.openProductDetailsScreen(it)
        }
        viewBinding.addProduct.setOnClickListener {
            viewModel.openAddProductScreen(args.category)
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner){
            if (it)
                viewBinding.progressBar.visibility = View.VISIBLE
            else viewBinding.progressBar.visibility = View.INVISIBLE
        }

//        viewBinding.productSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                lifecycleScope.launch {
//                    delay(300)
//                    viewModel.searchProduct(newText)
//                }
//                return true
//            }
//
//        })

    }

    private val listObserver = Observer<List<ProductData>> {
        if (it.isEmpty()) {
            viewBinding.emptyPlaceholder.visibility = View.VISIBLE
        } else {
            viewBinding.emptyPlaceholder.visibility = View.GONE
        }
        adapter.submitList(it)
    }
}