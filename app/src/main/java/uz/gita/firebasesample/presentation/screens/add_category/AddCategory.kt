package uz.gita.firebasesample.presentation.screens.add_category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.firebasesample.R
import uz.gita.firebasesample.databinding.ScreenAddCategoryBinding

// Created by Jamshid Isoqov an 9/18/2022
class AddCategory : Fragment(R.layout.screen_add_category) {
    private val viewBinding: ScreenAddCategoryBinding by viewBinding(ScreenAddCategoryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
