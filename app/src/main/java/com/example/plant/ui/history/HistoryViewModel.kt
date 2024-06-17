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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : MutableLiveData<Boolean> = _isLoading



    fun getHistoryList(token: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getHistoryList("Bearer $token")
        client.enqueue(object : Callback<HistoriesResponse> {
            override fun onResponse(
                call: Call<HistoriesResponse>,
                response: Response<HistoriesResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(responseBody != null){
                        _historyList.value = responseBody.data as List<DataItem>?
                    }
                }else{
                    _isLoading.value = false
                    Log.d(TAG, "on fail${response.message()}")
                }
            }

            override fun onFailure(call: Call<HistoriesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure ${t.message}")
            }


        })
    }

    companion object{
        const val TAG = "HistoryFragment"
    }

}