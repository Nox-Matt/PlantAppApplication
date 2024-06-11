package com.example.plant.ui.history

import android.content.res.TypedArray
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plant.ListHistory
import com.example.plant.R
import com.example.plant.data.FormList
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.Data
import com.example.plant.ui.network.response.DataItem
import com.example.plant.ui.network.response.HistoriesResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel:ViewModel() {
    private val _historyList = MutableLiveData<List<DataItem>?>()
    val historyList: MutableLiveData<List<DataItem>?> get() = _historyList

    init {
        getHistoryList()
    }


    fun setHistory(listHistory: ArrayList<DataItem>) {
        viewModelScope.launch {
            _historyList.value = listHistory
        }
    }

    fun getHistoryList(){
        val auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNzQwNDk1ODQtYzAwOC00MzBjLWE2ZTAtNzJiODFkYzQyZjEyIn0sImlhdCI6MTcxODA4MTE0MX0.AFJzmjxV82x1jYh0ZBEF0JEkd6AU7bBQPjm2K31pD0U"
        val client = ApiConfig.getApiService().getHistoryList("Bearer $auth")
        client.enqueue(object : Callback<HistoriesResponse> {
            override fun onResponse(
                call: Call<HistoriesResponse>,
                response: Response<HistoriesResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _historyList.value = responseBody.data as List<DataItem>?
                    }
                }else{
                    Log.d(TAG, "on fail${response.message()}")
                }
            }

            override fun onFailure(call: Call<HistoriesResponse>, t: Throwable) {
                TODO("Not yet implemented")
                Log.d(TAG, "onFailure ${t.message}")
            }


        })
    }

    companion object{
        const val TAG = "HistoryFragment"
    }

}