package uz.gita.firebasesample.presentation.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.firebasesample.databinding.ItemAttBinding

class AttrAdapter() : RecyclerView.Adapter<AttrAdapter.ViewHolder>() {

    private val data = ArrayList<Pair<String, String>>()

    fun submitList(list: List<Pair<String, String>>) {

        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemAttBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {

            binding.name.text =
                data[absoluteAdapterPosition].first
            binding.desc.text =
                data[absoluteAdapterPosition].second
        }
    }


    override fun getItemCount(): Int = data.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAttBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()
}