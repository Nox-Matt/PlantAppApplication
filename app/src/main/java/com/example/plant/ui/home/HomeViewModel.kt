package com.example.plant.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ListHistory
import com.example.plant.ui.history.HistoryViewModel
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.DataItem
import com.example.plant.ui.network.response.HistoriesResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _historyList = MutableLiveData<List<DataItem>?>()
    val historyList: MutableLiveData<List<DataItem>?> get() = _historyList



    fun getHistoryList(){
        val auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNzNlNTUwZGYtOTE4ZS00ZGI3LTljNWItYjk2NGRjZjcwYmJiIn0sImlhdCI6MTcxODEyNjI1OH0.ebu6LZ7qdp8V3W6cUnCnGaODvmxf7iKGqCoedgswnCE"
        val client = ApiConfig.getApiService().getHistoryList("Bearer $auth")
        client.enqueue(object : Callback<HistoriesResponse> {
            override fun onResponse(
                call: Call<HistoriesResponse>,
                response: Response<HistoriesResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _historyList.value = responseBody.data?.subList(0,2) as List<DataItem>?
                    }
                }else{
                    Log.d(HistoryViewModel.TAG, "on fail${response.message()}")
                }
            }

            override fun onFailure(call: Call<HistoriesResponse>, t: Throwable) {
                TODO("Not yet implemented")
                Log.d(HistoryViewModel.TAG, "onFailure ${t.message}")
            }


        })
    }


}