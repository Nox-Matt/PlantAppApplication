package com.example.plant.ui.form
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.ui.data.Comment

class CommentAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.txt_username)
        val timeTextView: TextView = itemView.findViewById(R.id.txtTime)
        val commentTextView: TextView = itemView.findViewById(R.id.txtComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.usernameTextView.text = comment.username
        holder.timeTextView.text = comment.date
        holder.commentTextView.text = comment.comment
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}