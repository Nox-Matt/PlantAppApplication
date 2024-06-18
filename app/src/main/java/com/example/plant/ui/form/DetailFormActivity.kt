package com.example.plant.ui.form

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.ActivityDetailFormBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import java.text.SimpleDateFormat
import java.util.Locale

class DetailFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFormBinding
    private val viewModel: DetailFormViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        val formTitle = intent.getStringExtra("form_title") ?: ""
        val formUsername = intent.getStringExtra("form_username") ?: ""
        val formDate = intent.getStringExtra("form_date") ?: ""
        val formId = intent.getStringExtra("form_id") ?: ""
        val formQuestion = intent.getStringExtra("form_description")?: ""
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = inputFormat.parse(formDate)
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val formattedDate = outputFormat.format(date!!)
            binding.detailDate.text = formattedDate
        } catch (e: Exception) {
            binding.detailDate.text = "Invalid date format"
        }



        dataStoreViewModel.getTokenKey().observe(this) { token ->
            binding.detailUsername.text = formUsername
            binding.detailQnA.text = formTitle
            binding.qnaQuestion.text = formQuestion
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
        }


        val recyclerView = binding.recycleComment
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CommentAdapter()
        recyclerView.adapter = adapter

        viewModel.commentList.observe(this) { discussion ->
            adapter.submitList(discussion)
            showLoading(false)
            binding.emptyFormCondition.visibility = if (discussion.isEmpty()) View.VISIBLE else View.GONE
            binding.recycleComment.visibility = if (discussion.isEmpty()) View.GONE else View.VISIBLE
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