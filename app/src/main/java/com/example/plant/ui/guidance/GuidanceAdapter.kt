package com.example.plant.ui.guidance


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.ListGuidance
import com.example.plant.databinding.ItemRowGuidanceBinding
import com.example.plant.databinding.ItemRowHistoryBinding
import com.example.plant.ui.history.HistoryAdapter

class GuidanceAdapter : ListAdapter<ListGuidance, GuidanceAdapter.ListViewHolder>(DIFF_CALLBACK){
    class ListViewHolder(val binding: ItemRowGuidanceBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(guidance: ListGuidance){
            binding.imgGuide.setImageResource(guidance.photo)
            binding.txtTitle.text = "${guidance.title}"
            binding.txtTime.text = "${guidance.time}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowGuidanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story =getItem(position)
        holder.bind(story)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListGuidance>() {
            override fun areItemsTheSame(
                oldItem: ListGuidance,
                newItem: ListGuidance
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListGuidance,
                newItem: ListGuidance
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}