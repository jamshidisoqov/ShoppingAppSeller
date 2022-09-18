package uz.gita.firebasesample.presentation.screens.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.gita.firebasesample.R
import uz.gita.firebasesample.data.models.local.ProductCategoryData

class CategoryAdapter:RecyclerView.Adapter<CategoryAdapter.Holder>() {

    private var data = ArrayList<ProductCategoryData>()

    private var itemClickListener : ((ProductCategoryData) -> Unit)? = null

    fun setOnClickItemListener(block:(ProductCategoryData) -> Unit){
        itemClickListener = block
    }

    fun submit(list: List<ProductCategoryData>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }


    inner class Holder(view:View):RecyclerView.ViewHolder(view) {

        init {
            setOnClickItemListener {
                itemClickListener?.invoke(data[absoluteAdapterPosition])
            }
        }

        fun onBind(){
            var image = itemView.findViewById<ImageView>(R.id.image_category)
            var name = itemView.findViewById<TextView>(R.id.category_name)
            Picasso.get().load(data[absoluteAdapterPosition].imageUrl).into(image)
            name.text = data[absoluteAdapterPosition].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.onBind()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}