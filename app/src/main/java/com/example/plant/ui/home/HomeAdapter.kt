package com.example.plant.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.ListHistory
import com.example.plant.databinding.ItemRowHistoryBinding
import com.example.plant.ui.detail.DetailActivity
import com.example.plant.ui.history.HistoryAdapter

class HomeAdapter : ListAdapter<ListHistory, HomeAdapter.ListViewHolder>(DIFF_CALLBACK) {


    class ListViewHolder(val binding: ItemRowHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(history : ListHistory) {
            binding.imgCardLeaf.setImageResource(history.photo)
            binding.namaPenyakit.text = "${history.name}"
            binding.percentage.text = "${history.percentage}"
            binding.time.text = "${history.time}"

            val intentDetail = Intent(itemView.context, DetailActivity::class.java)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.imgCardLeaf,"img_plant"),
                    Pair(binding.namaPenyakit, "name"),
                    Pair(binding.percentage, "percentage")

                )

            intentDetail.putExtra(DetailActivity.PHOTO_DETAIL, history.photo)
            intentDetail.putExtra(DetailActivity.DISEASE_NAME, history.name)
            intentDetail.putExtra(DetailActivity.PERCENTAGE, history.percentage)

            itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListHistory>() {
            override fun areItemsTheSame(
                oldItem: ListHistory,
                newItem: ListHistory
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListHistory,
                newItem: ListHistory
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}