package com.capstone.kuma.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import java.util.Date

data class RegisterResponse(
    @field:SerializedName("error")
    @Expose
    val error: Boolean,
    @field:SerializedName("message")
    @Expose
    val message: String
)

data class LoginResponse(
    @field:SerializedName("error")
    @Expose
    val error: Boolean,
    @field:SerializedName("message")
    @Expose
    val message: String,
    @field:SerializedName("loginResult")
    @Expose
    val loginResult: LoginResult,
)

data class LoginResult(
    @field:SerializedName("userId")
    @Expose
    val userId: String,
    @field:SerializedName("email")
    @Expose
    val email: String,
    @field:SerializedName("name")
    @Expose
    val name: String,
    @field:SerializedName("token")
    @Expose
    val token: String
)

data class UploadResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String
)

interface ApiService{
    @FormUrlEncoded
    @POST("/api/register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("/api/getallmoods")
    fun getAllMoods(

    )

    @Multipart
    @POST("api/new-mood-entry")
    fun upload(
        @Header("Authorization") Authorization:String,
        @Part("date") date: String,
        @Part("sub_mood") sub_mood: RequestBody,
        @Part("activities") activities: RequestBody,
        @Part("story") story: RequestBody,
    ): Call<UploadResponse>

    @PUT("api/update_data/{id}")
    fun update(

    )
}