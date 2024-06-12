package com.example.plant.ui.form

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.plant.data.FormList
import com.example.plant.databinding.ItemRowDiscussionBinding
import com.example.plant.ui.network.response.DataForumItem


class FormAdapter(private val onItemClick: (DataForumItem) -> Unit) : ListAdapter<DataForumItem, FormAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(val binding: ItemRowDiscussionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(form: DataForumItem) {
            binding.txtUsername.text = form.username ?: ""
            binding.txtTime.text = form.createdAt ?: ""
            binding.txtQnaTitle.text = form.title ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowDiscussionBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val form = getItem(position)
        holder.bind(form)
        holder.itemView.setOnClickListener { onItemClick(form) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataForumItem>() {
            override fun areItemsTheSame(oldItem: DataForumItem, newItem: DataForumItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataForumItem, newItem: DataForumItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}