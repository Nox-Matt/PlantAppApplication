package com.example.plant.ui.network

import com.example.plant.ui.network.response.Data
import com.example.plant.ui.network.response.DetectResponse
import com.example.plant.ui.network.response.LoginResponse
import com.example.plant.ui.network.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun Register(
        @Field("username") username:String,
        @Field("password") password:String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username:String,
        @Field("password") password:String,
    ): Call<LoginResponse>

    @Multipart
    @POST("detect")
    fun detectImage(
        @Header("Authorization") token:String,
        @Part file:MultipartBody.Part
    ): Call<DetectResponse>
}