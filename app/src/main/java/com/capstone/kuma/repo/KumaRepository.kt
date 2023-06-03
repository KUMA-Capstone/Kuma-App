package com.capstone.kuma.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.kuma.api.ApiConfig
import com.capstone.kuma.api.ApiService
import com.capstone.kuma.api.LoginResponse
import com.capstone.kuma.api.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KumaRepository(private val apiService: ApiService) {
    val registerResponse = MutableLiveData<RegisterResponse?>()
    val loginResponse = MutableLiveData<LoginResponse?>()

    fun registerLauncherRepo(name:String, email: String, password:String) {
        Log.d(".RegisterActivity", "$name, $email, $password")
        val launchRegister = ApiConfig.getApiService().postRegister(name, email, password)

        launchRegister.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        Log.d(".RegisterActivity","Register berhasil $responseBody")
                        registerResponse.postValue(responseBody)
                    }
                }else{
                    val errMess = when (response.code()) {
                        401 -> "${response.code()} : Bad Request"
                        403 -> "${response.code()} : Forbidden"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : $response"
                    }
                    Log.e(".RegisterActivity","$errMess")
                    registerResponse.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(".RegisterActivity","Register Failure")
            }
        })
    }

    fun loginLauncherRepo(email: String, password:String) {
        val launchLogin = ApiConfig.getApiService().login(email, password)
        launchLogin.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        Log.d(".LoginActivity","Login Berhasil $responseBody")
                        loginResponse.postValue(responseBody)
                    }
                }else{
                    Log.e(".LoginActivity","Login Gagal ${response.message()}")
                    loginResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(".RegisterActivity","Login Failure")
            }

        })
    }

    fun getRegisterResponse(): LiveData<RegisterResponse?> {
        return registerResponse
    }

    fun getLoginResponse(): LiveData<LoginResponse?> {
        return loginResponse
    }

    companion object {
        @Volatile
        private var instance: KumaRepository? = null
        fun getInstace(apiService: ApiService): KumaRepository = instance ?: synchronized(this){
            instance ?: KumaRepository(apiService)
        }.also { instance= it }
    }
}