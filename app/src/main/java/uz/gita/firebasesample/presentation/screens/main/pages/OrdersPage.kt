package uz.gita.firebasesample.presentation.screens.main.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collectLatest
import uz.gita.firebasesample.R
import uz.gita.firebasesample.databinding.PageOrdersBinding
import uz.gita.firebasesample.presentation.screens.adapter.OrderAdapter
import uz.gita.firebasesample.presentation.viewmodel.OrdersViewModel
import uz.gita.firebasesample.presentation.viewmodel.impl.OrdersViewModelImpl

// Created by Jamshid Isoqov an 9/18/2022
class OrdersPage : Fragment(R.layout.page_orders) {

    private val viewModel: OrdersViewModel by viewModels<OrdersViewModelImpl>()

    private val viewBinding: PageOrdersBinding by viewBinding()

    private val adapter: OrderAdapter by lazy {
        OrderAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.listOrders.adapter = adapter

        adapter.setUpdateListener {
            viewModel.updateOrder(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allOrders.collectLatest {
                adapter.submitList(it)
            }
        }

    }

}