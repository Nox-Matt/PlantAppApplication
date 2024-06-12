package com.example.plant.ui.form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ui.data.Comment
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.AnswersItem
import com.example.plant.ui.network.response.DataComment
import com.example.plant.ui.network.response.DetailForumResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFormViewModel : ViewModel() {
    private val _commentList = MutableLiveData<List<DataComment>>()
    val commentList: LiveData<List<DataComment>> get() = _commentList

    fun getCommentsForForum(forumId: String, token: String) {
        ApiConfig.getApiService().getForumDetail("Bearer $token", forumId).enqueue(object : Callback<DetailForumResponse> {
            override fun onResponse(
                call: Call<DetailForumResponse>,
                response: Response<DetailForumResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _commentList.value = (responseBody?.data?.answer ?: emptyList()) as List<DataComment>
                } else {
                    Log.e("DetailFormViewModel", "Error fetching comments: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailForumResponse>, t: Throwable) {
                Log.e("DetailFormViewModel", "Error fetching comments", t)
            }
        })
    }
    fun postComment(formId: String, commentText: String, token: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().postComment(formId, commentText, "Bearer $token")
//                _postCommentResponse.value = response.body()
            } catch (e: Exception) {
                Log.e("DetailFormViewModel", "Error posting comment: ${e.message}")
            }
        }
    }
}