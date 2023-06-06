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
import retrofit2.http.Path
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
    val message: String,
    @field:SerializedName("uploadResult")
    val uploadResult: uploadResult,
)

data class uploadResult(
    @field:SerializedName("id")
    @Expose
    val id: Int,
    @field:SerializedName("date")
    @Expose
    val date: String,
    @field:SerializedName("sub_mood")
    @Expose
    val sub_mood: String,
    @field:SerializedName("activities")
    @Expose
    val activities: String,
    @field:SerializedName("story")
    @Expose
    val story: String,
    @field:SerializedName("userId")
    @Expose
    val userId: String
)

data class UpdateResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
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

    @FormUrlEncoded
    @POST("api/new-mood-entry")
    fun upload(
        @Header("Authorization") Authorization:String,
        @Field("userId") userId: String,
        @Field("date") date: String,
        @Field("sub_mood") sub_mood: String,
        @Field("activities") activities: String,
        @Field("story") story: String,
    ): Call<UploadResponse>

    @FormUrlEncoded
    @PUT("api/update-user")
    fun updateData(
        @Field("userId") userId: String,
        @Field("name") name: String,
        @Field("password") password: String
    ) : Call<UpdateResponse>
}