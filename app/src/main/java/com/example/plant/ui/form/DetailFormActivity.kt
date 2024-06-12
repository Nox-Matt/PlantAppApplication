package com.example.plant.ui.form

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.data.FormList
import com.example.plant.databinding.ActivityDetailFormBinding

class DetailFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFormBinding
    private val viewModel: DetailFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formId = intent.getStringExtra("form_id") ?: ""
        val formTitle = intent.getStringExtra("form_title") ?: ""
        val formUsername = intent.getStringExtra("form_username") ?: ""
        val formDate = intent.getStringExtra("form_date") ?: ""


        binding.detailUsername.text = formUsername
        binding.detailDate.text = formDate
        binding.detailQnA.text = formTitle

        binding.imageButton.setOnClickListener {
            val commentText = binding.commentText.text.toString()
            if (commentText.isNotBlank()) {
                val formId = intent.getStringExtra("form_id") ?: ""
                val token =
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNzQwNDk1ODQtYzAwOC00MzBjLWE2ZTAtNzJiODFkYzQyZjEyIn0sImlhdCI6MTcxODA4MTE0MX0.AFJzmjxV82x1jYh0ZBEF0JEkd6AU7bBQPjm2K31pD0U"
                viewModel.postComment(formId, commentText, token)
                binding.commentText.text.clear()
            }
        }

        val recyclerView = binding.recycleComment
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.commentList.observe(this) { comments ->
            val adapter = CommentAdapter(comments)
            recyclerView.adapter = adapter
        }

        val back = binding.imgBack
        back.setOnClickListener {
            onBackPressed()
        }
    }
}