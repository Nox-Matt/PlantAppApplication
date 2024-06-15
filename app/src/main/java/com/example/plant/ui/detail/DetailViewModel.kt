package com.example.plant.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.Data
import com.example.plant.ui.network.response.DataDetail
import com.example.plant.ui.network.response.HistoryDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel(){
    private val _detaillist = MutableLiveData<DataDetail?>()
    val detaillist: MutableLiveData<DataDetail?> get() =_detaillist

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : MutableLiveData<Boolean> = _isLoading


    fun getDetail(token:String, id: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail("Bearer $token", id)
        client.enqueue(object:Callback<HistoryDetailResponse>{
            override fun onResponse(
                call: Call<HistoryDetailResponse>,
                response: Response<HistoryDetailResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(responseBody != null){
                        _detaillist.value = responseBody.data
                    }
                }else{
                    _isLoading.value = false
                    Log.d(TAG, "${response.message()}")
                }
            }

            override fun onFailure(call: Call<HistoryDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "${t.message}")
            }

        })
    }

    companion object{
        const val TAG = "DetailActivity"
    }
}