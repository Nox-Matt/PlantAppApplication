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


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : MutableLiveData<Boolean> = _isLoading


    fun getFormList(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getForumList("Bearer $token")
        client.enqueue(object : Callback<ForumResponse> {
            override fun onResponse(
                call: Call<ForumResponse>,
                response: Response<ForumResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value =false
                    val responseBody = response.body()
                    if(responseBody != null){
                        _formList.value = responseBody.data as List<DataForumItem>
                    }
                } else{
                    _isLoading.value = false
                    Log.d(TAG, "on fail${response.message()}")
                }
            }
            override fun onFailure(call: Call<ForumResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure ${t.message}")
            }
        })
    }
    companion object{
        private const val TAG = "FormViewModel"
    }
}