package com.example.plant.ui.form
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.ui.data.Comment
import com.example.plant.ui.network.response.DataComment

class CommentAdapter(private val comments: List<DataComment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.txt_username)
        val timeTextView: TextView = itemView.findViewById(R.id.txtTime)
        val commentTextView: TextView = itemView.findViewById(R.id.txtComment)
        val commentTextCount: TextView = itemView.findViewById(R.id.commentTextCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.usernameTextView.text = comment.username ?: ""
        holder.timeTextView.text = comment.createdAt ?: ""
        holder.commentTextView.text = comment.question ?: ""
        holder.commentTextCount.text = "${comments.size} Comments"
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}