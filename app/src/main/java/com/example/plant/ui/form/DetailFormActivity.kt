package com.example.plant.ui.form

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.ActivityDetailFormBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore

class DetailFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFormBinding
    private val viewModel: DetailFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formTitle = intent.getStringExtra("form_title") ?: ""
        val formUsername = intent.getStringExtra("form_username") ?: ""
        val formDate = intent.getStringExtra("form_date") ?: ""
        val formId = intent.getStringExtra("form_id") ?: ""

        binding.detailUsername.text = formUsername
        binding.detailDate.text = formDate
        binding.detailQnA.text = formTitle
        val pref = UserPreference.getInstance(this.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DataStoreViewModel::class.java)

        datastoreViewModel.getTokenKey().observe(this){
            viewModel.getCommentsForForum(formId, it)
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.imageButton.setOnClickListener {
            datastoreViewModel.getTokenKey().observe(this){
                val commentText = binding.commentText.text.toString()
                if (commentText.isNotBlank()) {
                    viewModel.postComment(it, formId, commentText)
                    binding.commentText.text.clear()
                    viewModel.getCommentsForForum(formId, it)
                } else {
                    binding.commentText.error = "Please enter a comment"
                }
            }
        }

        val recyclerView = binding.recycleComment
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CommentAdapter()
        recyclerView.adapter = adapter

        viewModel.commentList.observe(this) { discussion ->
            adapter.submitList(discussion)
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