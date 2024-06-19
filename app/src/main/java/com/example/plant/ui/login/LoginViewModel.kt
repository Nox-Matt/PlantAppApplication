package com.example.plant.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.LoginResponse
import com.example.plant.ui.register.RegisterActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class LoginViewModel : ViewModel(){
    private val _isError= MutableLiveData<Boolean>()
    val isError :MutableLiveData<Boolean> = _isError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : MutableLiveData<Boolean>  = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty : MutableLiveData<Boolean> = _isEmpty

    private val _token = MutableLiveData<String?>()
    val token : MutableLiveData<String?> = _token

    fun login(username: String, password: String) {
        val client = ApiConfig.getApiService().login(username, password)
        if (username.isEmpty() || password.isEmpty()) {
            Log.d(LoginActivity.TAG, "login: username or password is empty")
            _isEmpty.value = true
            return
        }
        _isLoading.value = true
        _isError.value = false
        _isEmpty.value = false
        client.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
//                        _isLoading.value = false
                    _isError.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(LoginActivity.TAG, "${responseBody.token}")
                        _token.value = "${responseBody.token}"
                    } else {
                        Log.d(RegisterActivity.TAG, "onResponseNull ${response.message()}")
                    }
                } else {
//                        _isLoading.value = false
                    val errorBody = (response.errorBody() as ResponseBody).string()
                    val error1 = errorBody.split("{")
                    val error2 = error1[1].split("}")
                    val error3 = error2[0].split(",")
                    val error4 = error3[1].split(":")
                    val error5 = error4[1].split("\"")
                    val errorfinal = error5[1]
                    Log.d(LoginActivity.TAG, errorfinal)
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                    _isLoading.value = false
                Log.d(RegisterActivity.TAG, "onFailure: ${t.message}")
            }

        })

    }

    companion object{
        const val TAG = "LoginActivity"
    }
}