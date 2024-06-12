package com.example.plant.ui.form

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plant.R
import com.example.plant.databinding.ActivityAddFormBinding
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.AddForumResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFormBinding
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
        binding.btnSubmitQuestion.setOnClickListener {
            val auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNzQwNDk1ODQtYzAwOC00MzBjLWE2ZTAtNzJiODFkYzQyZjEyIn0sImlhdCI6MTcxODA4MTE0MX0.AFJzmjxV82x1jYh0ZBEF0JEkd6AU7bBQPjm2K31pD0U"
            val title = binding.editTitleQuestion.text.toString()
            val question = binding.editQuestion.text.toString()

            val apiService = ApiConfig.getApiService()
            val call = apiService.addForum(auth,title, question)

            call.enqueue(object : Callback<AddForumResponse> {
                override fun onResponse(
                    call: Call<AddForumResponse>,
                    response: Response<AddForumResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Toast.makeText(this@AddFormActivity, responseBody?.message, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Log.e("AddFormActivity", "Error creating forum: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<AddForumResponse>, t: Throwable) {
                    Log.e("AddFormActivity", "Network failure: ${t.message}")
                }
            })
        }
    }
}
