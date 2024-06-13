package com.example.plant.ui.guidance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ListGuidance
import com.example.plant.ListHistory
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.DataGuide
import com.example.plant.ui.network.response.GuidanceResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuidanceViewModel: ViewModel() {
    private val _guidanceList = MutableLiveData<List<DataGuide?>?>()
    val guidanceList: LiveData<List<DataGuide?>?> get() = _guidanceList

    fun setGuidance(listGuidance: List<DataGuide>) {
        viewModelScope.launch {
            _guidanceList.value = listGuidance
        }
    }

    fun getGuidanceList(token:String){
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNzNlNTUwZGYtOTE4ZS00ZGI3LTljNWItYjk2NGRjZjcwYmJiIn0sImlhdCI6MTcxODEyNjI1OH0.ebu6LZ7qdp8V3W6cUnCnGaODvmxf7iKGqCoedgswnCE"
        val client = ApiConfig.getApiService().getGuidanceList("Bearer $token")
        client.enqueue(object: Callback<GuidanceResponse>{
            override fun onResponse(
                call: Call<GuidanceResponse>,
                response: Response<GuidanceResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _guidanceList.value = responseBody.data
                    }

                }
            }

            override fun onFailure(call: Call<GuidanceResponse>, t: Throwable) {
                Log.d(TAG, "${t.message}")
            }

        })
    }

    companion object{
        const val TAG = "GuidanceFragment"
    }
}