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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : MutableLiveData<Boolean> = _isLoading




    fun getGuidanceList(token:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGuidanceList("Bearer $token")
        client.enqueue(object: Callback<GuidanceResponse>{
            override fun onResponse(
                call: Call<GuidanceResponse>,
                response: Response<GuidanceResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null){
                        _guidanceList.value = responseBody.data
                    }
                }else{
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<GuidanceResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "${t.message}")
            }

        })
    }

    companion object{
        const val TAG = "GuidanceFragment"
    }
}