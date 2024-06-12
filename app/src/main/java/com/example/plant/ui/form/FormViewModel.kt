package com.example.plant.ui.form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.DataForumItem
import com.example.plant.ui.network.response.ForumResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormViewModel : ViewModel() {

    private val _formList = MutableLiveData<List<DataForumItem>>()
    val formList: LiveData<List<DataForumItem>> get() = _formList


    fun getFormList() {
        val auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNWYzMjg1MTktNTU5My00YWE4LThhNjgtZTQwOGE2ZGY3NjRjIn0sImlhdCI6MTcxODIwMjUwNX0.LgM5PsW3Y1QUtKxDMSi9dagxpgNy-bVeNidGlzk2uqc"
        val client = ApiConfig.getApiService().getForumList("Bearer $auth")
        client.enqueue(object : Callback<ForumResponse> {
            override fun onResponse(
                call: Call<ForumResponse>,
                response: Response<ForumResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null){
                        _formList.value = responseBody.data as List<DataForumItem>
                    }
                } else{
                    Log.d(TAG, "on fail${response.message()}")
                }
            }
            override fun onFailure(call: Call<ForumResponse>, t: Throwable) {
                Log.d(TAG, "onFailure ${t.message}")
            }
        })
    }
    companion object{
        private const val TAG = "FormViewModel"
    }
}