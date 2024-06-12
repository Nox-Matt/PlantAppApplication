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


    fun getDetail(id: String){
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiNzNlNTUwZGYtOTE4ZS00ZGI3LTljNWItYjk2NGRjZjcwYmJiIn0sImlhdCI6MTcxODEyNjI1OH0.ebu6LZ7qdp8V3W6cUnCnGaODvmxf7iKGqCoedgswnCE"
        val client = ApiConfig.getApiService().getDetail("Bearer $token", id)
        client.enqueue(object:Callback<HistoryDetailResponse>{
            override fun onResponse(
                call: Call<HistoryDetailResponse>,
                response: Response<HistoryDetailResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _detaillist.value = responseBody.data
                    }
                }else{
                    Log.d(TAG, "${response.message()}")
                }
            }

            override fun onFailure(call: Call<HistoryDetailResponse>, t: Throwable) {
                Log.d(TAG, "${t.message}")
            }

        })
    }

    companion object{
        const val TAG = "DetailActivity"
    }
}