package uz.gita.firebasesample.presentation.screens.products.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.firebasesample.R
import uz.gita.firebasesample.databinding.ScreenProductDetailsBinding

// Created by Jamshid Isoqov an 9/18/2022
@AndroidEntryPoint
class ProductDetailsScreen : Fragment(R.layout.screen_product_details) {
    private val viewBinding: ScreenProductDetailsBinding by viewBinding(ScreenProductDetailsBinding::bind)
    private val args: ProductDetailsScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var attrs = ""
        val list = args.product.attrs
        for (i in list.indices) {
            attrs += "${list[i].first}\t\t\t${list[i].second}"
        }

        Glide.with(this)
            .load(args.product.photos)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(viewBinding.ivProductImg)

        viewBinding.tvProductName.text = args.product.name
        viewBinding.tvProductDescription.text = args.product.description
        viewBinding.tvProductPrice.text = args.product.sell
        viewBinding.tvProductAttrs.text = attrs
    }
}