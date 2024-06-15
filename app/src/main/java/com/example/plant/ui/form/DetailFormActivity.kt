package com.example.plant.ui.form

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.databinding.ActivityDetailFormBinding

class DetailFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFormBinding
    private val viewModel: DetailFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        val formTitle = intent.getStringExtra("form_title") ?: ""
        val formUsername = intent.getStringExtra("form_username") ?: ""
        val formDate = intent.getStringExtra("form_date") ?: ""
        val formId = intent.getStringExtra("form_id") ?: ""
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNWYzMjg1MTktNTU5My00YWE4LThhNjgtZTQwOGE2ZGY3NjRjIn0sImlhdCI6MTcxODIwMjUwNX0.LgM5PsW3Y1QUtKxDMSi9dagxpgNy-bVeNidGlzk2uqc"

        binding.detailUsername.text = formUsername
        binding.detailDate.text = formDate
        binding.detailQnA.text = formTitle
        viewModel.getCommentsForForum(formId, token)


        binding.imageButton.setOnClickListener {
            val commentText = binding.commentText.text.toString()
            if (commentText.isNotBlank()) {
                viewModel.postComment(token, formId, commentText)
                binding.commentText.text.clear()
                viewModel.getCommentsForForum(formId, token)
            } else {
                binding.commentText.error = "Please enter a comment"
            }
        }

        val recyclerView = binding.recycleComment
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CommentAdapter()
        recyclerView.adapter = adapter

        viewModel.commentList.observe(this) { discussion ->
            adapter.submitList(discussion)
            showLoading(false)
        }

        val back = binding.imgBack
        back.setOnClickListener {
            onBackPressed()
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}