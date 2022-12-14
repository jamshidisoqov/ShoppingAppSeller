package uz.gita.firebasesample.presentation.screens.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.models.local.ProductData
import java.lang.Exception

class ProductAdapter : ListAdapter<ProductData, ProductAdapter.Holder>(Callback) {

    private var itemOnClickListener: ((ProductData) -> Unit)? = null

    fun setItemOnClickListener(block: ((ProductData) -> Unit)) {
        itemOnClickListener = block
    }

    object Callback : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id && oldItem.description == newItem.description && oldItem.categoryId == newItem.categoryId
                    && oldItem.name == newItem.name && oldItem.attrs == newItem.attrs
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val productName: TextView = view.findViewById(R.id.product_name)
        private val card: MaterialCardView = view.findViewById(R.id.card)
        private val productPrice: TextView = view.findViewById(R.id.product_price)
        private val productPhoto: ImageView = view.findViewById(R.id.product_image)
        private val progressBar: ProgressBar = view.findViewById(R.id.progress_barrrr)

        init {
            card.setOnClickListener {
                itemOnClickListener?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        fun bind(position: Int) {
            val item = getItem(position)

            productName.text = item.name
            productPrice.text = item.sell

            Picasso
                .get()
                .load(item.photos)
                .into(productPhoto, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {}
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }
}