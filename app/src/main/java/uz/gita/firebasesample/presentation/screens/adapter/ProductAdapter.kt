package uz.gita.client.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.models.local.ProductData

class ProductAdapter: ListAdapter<ProductData, ProductAdapter.Holder>(Callback) {

    private var itemOnClickListener: ((ProductData) ->Unit)? = null

    fun setItemOnClickListener(block: ((ProductData) -> Unit)){
        itemOnClickListener = block
    }

    object Callback: DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id && oldItem.description == newItem.description && oldItem.categoryId == newItem.categoryId
                    && oldItem.name == newItem.name && oldItem.attrs == newItem.attrs
        }

    }

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {

        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productPhoto: ImageView = view.findViewById(R.id.product_image)
        val toCart: TextView = view.findViewById(R.id.product_savat)

        fun bind(position: Int) {

            val item = getItem(position)

            productName.text = item.name
            productPrice.text = item.sell



            Glide
                .with(itemView)
                .load(item.photos)
                .placeholder(R.drawable.logo)
                .into(productPhoto)

            toCart.setOnClickListener{
                itemOnClickListener?.invoke(item)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }
}