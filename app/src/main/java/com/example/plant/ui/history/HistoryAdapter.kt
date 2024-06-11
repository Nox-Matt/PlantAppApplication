package com.example.plant.ui.history

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.ListHistory
import com.example.plant.databinding.ItemRowHistoryBinding
import com.example.plant.ui.detail.DetailActivity
import androidx.core.util.Pair
import com.bumptech.glide.Glide
import com.example.plant.ui.network.response.Data
import com.example.plant.ui.network.response.DataItem

class HistoryAdapter: ListAdapter<DataItem, HistoryAdapter.ListViewHolder>(DIFF_CALLBACK) {


    class ListViewHolder(val binding: ItemRowHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(history : DataItem) {
            Glide.with(itemView.context)
                .load("${history.imageUrl}")
                .into(binding.imgCardLeaf)
            binding.namaPenyakit.text = "${history.diseasesName}"
            binding.percentage.text = "${history.percentage}"
            binding.time.text = "${history.createdAt}"

            itemView.setOnClickListener {
                val intentDetail =Intent(itemView.context, DetailActivity::class.java)
                val optionsCompat: ActivityOptionsCompat=
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgCardLeaf,"img_plant"),
                        Pair(binding.namaPenyakit, "name"),
                        Pair(binding.percentage, "percentage")

                    )
                intentDetail.putExtra(DetailActivity.PHOTO_DETAIL, history.imageUrl)
                intentDetail.putExtra(DetailActivity.DISEASE_NAME, history.diseasesName)
                intentDetail.putExtra(DetailActivity.PERCENTAGE, "${history.percentage}")


                itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history =getItem(position)
        holder.bind(history)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}