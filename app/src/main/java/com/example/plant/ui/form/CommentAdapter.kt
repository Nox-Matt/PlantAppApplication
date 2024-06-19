package com.example.plant.ui.form
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.databinding.ItemRowCommentBinding
import com.example.plant.ui.network.response.AnswersItem
import com.example.plant.ui.network.response.DataComment
import java.text.SimpleDateFormat
import java.util.Locale

class CommentAdapter : ListAdapter<AnswersItem, CommentAdapter.CommentViewHolder>(DIFF_CALLBACK) {

    class CommentViewHolder(private val binding: ItemRowCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: AnswersItem) {
            binding.txtUsername.text = comment.username ?: ""
            binding.txtComment.text = comment.answer ?: ""
            val createdAt = comment.createdAt
            if (createdAt != null) {
                try {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val date = inputFormat.parse(createdAt)
                    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    val formattedDate = outputFormat.format(date!!)
                    binding.txtTime.text = formattedDate
                } catch (e: Exception) {
                    binding.txtTime.text = "Invalid date format"
                }
            } else {
                binding.txtTime.text = ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemRowCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val commentItem = getItem(position)
        holder.bind(commentItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnswersItem>() {
            override fun areItemsTheSame(oldItem: AnswersItem, newItem: AnswersItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AnswersItem, newItem: AnswersItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}