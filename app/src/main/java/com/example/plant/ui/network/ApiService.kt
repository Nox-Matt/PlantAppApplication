package com.example.plant.ui.network

import com.example.plant.ui.network.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun Register(
        @Field("username") username:String,
        @Field("password") password:String,
    ): Call<RegisterResponse>
}