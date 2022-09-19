package uz.gita.firebasesample.presentation.screens.products.add

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.models.local.ProductData
import uz.gita.firebasesample.databinding.AttrDialogBinding
import uz.gita.firebasesample.databinding.ImagePickerBinding
import uz.gita.firebasesample.databinding.ScreenAddProductBinding
import uz.gita.firebasesample.presentation.screens.adapter.AttrAdapter
import uz.gita.firebasesample.presentation.viewmodel.AddProductViewModel
import uz.gita.firebasesample.presentation.viewmodel.impl.AddProductViewModelImpl
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


// Created by Jamshid Isoqov an 9/18/2022

@AndroidEntryPoint
class AddProduct : Fragment(R.layout.screen_add_product) {

    private val binding: ScreenAddProductBinding by viewBinding(ScreenAddProductBinding::bind)
    private val viewModel: AddProductViewModel by viewModels<AddProductViewModelImpl>()
    private var uri: Uri? = null
    private lateinit var url: String
    private val adapter: AttrAdapter by lazy { AttrAdapter() }
    private val attrList = ArrayList<Pair<String, String>>()
    private val args: AddProductArgs by navArgs()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.image.setOnClickListener {
            showGalleryDialog()
        }

        binding.add.setOnClickListener {
            viewModel.addProduct(
                ProductData(
                    categoryId = args.category.id,
                    name = binding.etName.text.toString(),
                    description = binding.etDesc.text.toString(),
                    photos = "salom",
                    sell = binding.etPrice.text.toString(),
                    attrs = attrList
                )
            )
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            if (it)
                binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.INVISIBLE
        }

        binding.addBtn.setOnClickListener {
            addAttr()
        }

        binding.rv.adapter = adapter
        viewModel.messageLiveData.observe(this, messageObserver)

    }

    private val messageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun addAttr() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val dialogBinding =
            AttrDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        dialogBinding.save.setOnClickListener {

            attrList.add(
                Pair(
                    dialogBinding.name.text.toString(),
                    dialogBinding.desc.text.toString()
                )
            )
            adapter.submitList(attrList)

            dialog.dismiss()
        }
        dialog.setView(dialogBinding.root)
        dialog.show()
    }

    private fun showGalleryDialog() {

        val dialog = BottomSheetDialog(requireContext())
        val dialogView =
            ImagePickerBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        dialogView.camera.setOnClickListener {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
            dialog.cancel()
        }

        dialogView.gallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 456)
            dialog.cancel()
        }

        dialog.setContentView(dialogView.root)
        dialog.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 123) {
            val bmp = data?.extras?.get("data") as? Bitmap?
            binding.image.setImageBitmap(bmp)
            if (bmp != null) {
                viewModel.loadImage(getImageUri(requireContext(), bmp)!!)
            } else {
                Toast.makeText(requireContext(), "Rasm tanlanmadi", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == 456) {
            binding.image.setImageURI(data?.data)
            uri = data?.data
            if (uri != null) {
                viewModel.loadImage(uri!!)
            } else {
                Toast.makeText(requireContext(), "Rasm tanlanmadi", Toast.LENGTH_SHORT).show()
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
}