package uz.gita.firebasesample.presentation.screens.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.models.local.ProductCategoryData
import java.lang.Exception

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.Holder>() {

    private var data = ArrayList<ProductCategoryData>()

    private var itemClickListener: ((ProductCategoryData) -> Unit)? = null

    fun setOnClickItemListener(block: (ProductCategoryData) -> Unit) {
        itemClickListener = block
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submit(list: List<ProductCategoryData>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }


    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                itemClickListener?.invoke(data[absoluteAdapterPosition])
            }
        }

        fun onBind() {
            val image = itemView.findViewById<ImageView>(R.id.image_category)
            val name = itemView.findViewById<TextView>(R.id.category_name)
            val progress = itemView.findViewById<ProgressBar>(R.id.progress)

            Picasso.get().load(data[absoluteAdapterPosition].imageUrl)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        progress.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {}
                })

            name.text = data[absoluteAdapterPosition].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.onBind()
    }

    override fun getItemCount(): Int {
        return data.size
    }

}