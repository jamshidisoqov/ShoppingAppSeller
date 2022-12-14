package uz.gita.firebasesample.presentation.screens.add_category

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toIcon
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import uz.gita.firebasesample.databinding.DialogChoiceBinding
import uz.gita.firebasesample.databinding.ScreenAddCategoryBinding
import uz.gita.firebasesample.presentation.viewmodel.AddCategoryViewModel
import uz.gita.firebasesample.presentation.viewmodel.impl.AddCategoryViewModelImpl
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

// Created by Jamshid Isoqov an 9/18/2022

@AndroidEntryPoint
class AddCategory : Fragment(R.layout.screen_add_category) {
    private val viewBinding: ScreenAddCategoryBinding by viewBinding(ScreenAddCategoryBinding::bind)
    private val viewModel: AddCategoryViewModel by viewModels<AddCategoryViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.closeScreenLiveData.observe(this) {
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.ivAddBtn.setOnClickListener {
            addPictureToCategoryImage()
        }

        viewBinding.btnAddCategory.setOnClickListener {
            if (viewBinding.etNameCategory.text.isNotEmpty() && viewBinding.etTagCategory.text.isNotEmpty()) {
                val split = viewBinding.etTagCategory.text.split("#")
                viewModel.addCategory(
                    ProductCategoryData(
                        name = viewBinding.etNameCategory.text.toString(),
                        tags = split.toList()
                    )
                )
            }
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            if (it) viewBinding.progressBar.visibility = View.VISIBLE
            else viewBinding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            val bmp = data?.extras?.get("data") as Bitmap?

            if (bmp != null) {
                viewModel.uploadImage(getImageUri(requireContext(), bmp))
                viewBinding.tvImageCategory.setImageBitmap(bmp)
            }
        } else if (requestCode == 456) {
            if (data?.data != null) {
                viewModel.uploadImage(data.data!!)
                viewBinding.tvImageCategory.setImageURI(data.data)
            }
        }
    }
    private fun getImageUri(inContext: Context?, inImage: Bitmap): Uri {

        val tempFile = File.createTempFile("temprentpk", ".png")
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        val fileOutPut = FileOutputStream(tempFile)
        fileOutPut.write(bitmapData)
        fileOutPut.flush()
        fileOutPut.close()
        return Uri.fromFile(tempFile)
    }

    private fun addPictureToCategoryImage() {
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogChoiceBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        val alertDialog = builder.create()
        alertDialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        binding.btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
            alertDialog.dismiss()
        }
        binding.btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 456)
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

}
