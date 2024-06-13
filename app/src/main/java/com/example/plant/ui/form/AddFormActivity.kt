package com.example.plant.ui.form

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plant.R
import com.example.plant.databinding.ActivityAddFormBinding
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.ApiService
import com.example.plant.ui.network.response.AddForumResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFormBinding
    private val viewModel: AddFormViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddFormBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.imgBack.setOnClickListener{
            onBackPressed()
        }
        viewModel.apiService = ApiConfig.getApiService()
        binding.btnSubmitQuestion.setOnClickListener {
            val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNWYzMjg1MTktNTU5My00YWE4LThhNjgtZTQwOGE2ZGY3NjRjIn0sImlhdCI6MTcxODI1MjU1M30.y9B-GFuTuiD8bRAKe6LYfWecaCNiNstQXDzyr9WkFHA"
            val auth = "Bearer $token"
            val title = binding.editTitleQuestion.text.toString()
            val question = binding.editQuestion.text.toString()
            viewModel.addForum(auth, title, question)
        }
    }
}

