package com.example.plant.ui.form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ui.data.Comment
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.ApiService
import com.example.plant.ui.network.response.AnswersItem
import com.example.plant.ui.network.response.CommentResponse
import com.example.plant.ui.network.response.DataComment
import com.example.plant.ui.network.response.DetailForumResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFormViewModel : ViewModel() {
    private val _commentList = MutableLiveData<List<AnswersItem>>()
    val commentList: LiveData<List<AnswersItem>> get() = _commentList

    fun getCommentsForForum(forumId: String, token: String) {
        ApiConfig.getApiService().getForumDetail("Bearer $token", forumId).enqueue(object : Callback<DetailForumResponse> {
            override fun onResponse(
                call: Call<DetailForumResponse>,
                response: Response<DetailForumResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _commentList.value = (responseBody?.data?.answers ?: emptyList()) as List<AnswersItem>?
                } else {
                    Log.e("DetailFormViewModel", "Error fetching comments: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailForumResponse>, t: Throwable) {
                Log.e("DetailFormViewModel", "Error fetching comments", t)
            }
        })
    }
    fun postComment(token: String, formId: String, commentText: String) {
        viewModelScope.launch {
            try {
                val answerRequest = ApiService.Answer(commentText)
                val response = ApiConfig.getApiService().postComment("Bearer $token", formId, answerRequest)

                if (response.isSuccessful) {
                    getCommentsForForum(formId, token)
                } else {
                    Log.e("DetailFormViewModel", "Error posting comment: ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e("DetailFormViewModel", "Error posting comment: ${e.message}")
            }
        }
    }

}