package com.example.plant.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.DeleteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteViewModel:ViewModel() {
    private var _isSuccess = MutableLiveData<Boolean>()
    var isSuccess:MutableLiveData<Boolean> = _isSuccess

    private var _isClickId = MutableLiveData<Boolean>()
    var isClickId:MutableLiveData<Boolean> = _isClickId

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading:MutableLiveData<Boolean> = _isLoading

    private var _idDelete = MutableLiveData<String>()
    var idDelete : MutableLiveData<String> = _idDelete



    fun deleteHisotrybyId(token:String, id:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().deletehistoryId("Bearer $token", "id")
        client.enqueue(object:Callback<DeleteResponse>{
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(responseBody != null){
                        _isSuccess.value = true
                    }
                }else{
                    _isLoading.value = false
                    _isSuccess.value = false
                }
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = false
            }

        })
    }

    fun setIsClickId(isclick: Boolean){
        _isClickId.value = isclick
    }

    fun setIdDelete(id:String){
        _idDelete.value = id
    }


}