package com.example.plant.ui.network

import com.example.plant.ui.network.response.AddForumResponse
import com.example.plant.ui.network.response.CommentResponse
import com.example.plant.ui.network.response.DetailForumResponse
import com.example.plant.ui.network.response.DetectResponse
import com.example.plant.ui.network.response.ForumResponse
import com.example.plant.ui.network.response.GuidanceResponse
import com.example.plant.ui.network.response.HistoriesResponse
import com.example.plant.ui.network.response.HistoryDetailResponse
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
import retrofit2.http.Path

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

    @GET("detect/histories/{id}")
    fun getDetail(
        @Header("Authorization") token: String,
        @Path("id") id:String
    ):Call<HistoryDetailResponse>

    @GET("forum")
    fun getForumList(
        @Header("Authorization") token:String
    ): Call<ForumResponse>

    @GET("forum/{id}/answers")
    fun postComment(
        @Header("Authorization") token:String,
        @Path("id") id:String,
        @Field("answer") answer:String
    ): Call<CommentResponse>
    @GET("forum/{id}")
    fun getForumDetail(
        @Header("Authorization") token:String,
        @Path("id") id:String
    ): Call<DetailForumResponse>

    @FormUrlEncoded
    @POST("forum")
    fun addForum(
        @Header("Authorization") token:String,
        @Field("title") title: String,
        @Field("question") question: String
    ): Call<AddForumResponse>

    @Multipart
    @POST("detect")
    fun detectImage(
        @Header("Authorization") token:String,
        @Part file:MultipartBody.Part
    ): Call<DetectResponse>

    @GET("detect/histories")
    fun getHistoryList(
        @Header("Authorization") token :String
    ): Call<HistoriesResponse>

    @GET("guides")
    fun getGuidanceList(
        @Header("Authorization") token : String
    ):Call<GuidanceResponse>
}