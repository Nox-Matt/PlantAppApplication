package com.example.plant.ui.guidance


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plant.databinding.ItemRowGuidanceBinding
import com.example.plant.databinding.ItemRowHistoryBinding
import com.example.plant.ui.history.HistoryAdapter
import com.example.plant.ui.network.response.DataGuide

class GuidanceAdapter : ListAdapter<DataGuide, GuidanceAdapter.ListViewHolder>(DIFF_CALLBACK){
    class ListViewHolder(val binding: ItemRowGuidanceBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(guidance: DataGuide){
            binding.txtTitle.text = "${guidance.title}"

            Glide.with(itemView.context)
                .load("${guidance.imageUrl}")
                .into(binding.imgGuide)
            binding.txtTime.text = "${guidance.publishedAt}"



            itemView.setOnClickListener {
                val intentDetailGuide = Intent(itemView.context, DetailGuidanceActivity::class.java)
                intentDetailGuide.putExtra(DetailGuidanceActivity.ID, "${guidance.id}")
                itemView.context.startActivity(intentDetailGuide)
            }
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
        const val TAG = "guide id"
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataGuide>() {
            override fun areItemsTheSame(
                oldItem: DataGuide,
                newItem: DataGuide
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataGuide,
                newItem: DataGuide
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}