package com.example.plant.ui.form

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ui.network.ApiService
import com.example.plant.ui.network.response.AddForumResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFormViewModel : ViewModel() {
    lateinit var apiService: ApiService

    fun addForum(auth: String, title: String, question: String) {
        val formRequest = ApiService.FormRequest(title, question)
        val call = apiService.addForum(auth, formRequest)

        call.enqueue(object : Callback<AddForumResponse> {
            override fun onResponse(
                call: Call<AddForumResponse>,
                response: Response<AddForumResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("AddFormViewModel", "Forum created: ${responseBody?.message}")
                } else {
                    Log.e("AddFormViewModel", "Error creating forum: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddForumResponse>, t: Throwable) {
                Log.e("AddFormViewModel", "Network failure: ${t.message}")
            }
        })
    }
}