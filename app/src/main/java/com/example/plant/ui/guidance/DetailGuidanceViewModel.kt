package com.example.plant.ui.guidance

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.ContentItem
import com.example.plant.ui.network.response.DataDetailGuide
import com.example.plant.ui.network.response.DetailGuidanceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailGuidanceViewModel: ViewModel() {
    private val _dataList = MutableLiveData<DataDetailGuide?>()
    val dataList:MutableLiveData<DataDetailGuide?> get() = _dataList

    private val _contentList = MutableLiveData<List<ContentItem?>?>()
    val contentList :MutableLiveData<List<ContentItem?>?> get() = _contentList


    fun getDetailGuide(token:String, id:String){
        val client = ApiConfig.getApiService().getGuidanceDetail("Bearer $token", id)
        client.enqueue(object:Callback<DetailGuidanceResponse>{
            override fun onResponse(
                call: Call<DetailGuidanceResponse>,
                response: Response<DetailGuidanceResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!= null){
                        _dataList.value = responseBody.data
                        _contentList.value  = responseBody.data?.content
                    }
                }else{
                    Log.d(TAG, "${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailGuidanceResponse>, t: Throwable) {
                Log.d(TAG, "${t.message}")
            }

        })
    }




    companion object{
        const val TAG = "DetailGuidanceActivity"
    }
}