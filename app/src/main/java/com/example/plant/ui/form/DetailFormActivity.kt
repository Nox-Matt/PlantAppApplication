package com.example.plant.ui.form

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

        val form = intent.getParcelableExtra<FormList>("form")
        form?.let {
            binding.detailUsername.text = it.name
            binding.detailDate.text = it.date
            binding.detailQnA.text = it.title
        }

        // Initialize RecyclerView and Adapter
        val recyclerView = binding.recycleComment
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.commentList.observe(this, Observer { comments ->
            val adapter = CommentAdapter(comments)
            recyclerView.adapter = adapter
        })
    }
}