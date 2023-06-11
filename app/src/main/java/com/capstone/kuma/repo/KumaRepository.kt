package com.capstone.kuma.repo

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.capstone.kuma.LoginSession
import com.capstone.kuma.SessionPreference
import com.capstone.kuma.api.ApiConfig
import com.capstone.kuma.api.ApiService
import com.capstone.kuma.api.LoginResponse
import com.capstone.kuma.api.MoodResponse
import com.capstone.kuma.api.RegisterResponse
import com.capstone.kuma.api.UpdateResponse
import com.capstone.kuma.api.moodResult
import com.capstone.kuma.api.signinResult
import com.capstone.kuma.data.MoodPagingSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KumaRepository(private val apiService: ApiService) {
    val reportData = MutableLiveData<List<moodResult>>()
    val registerResponse = MutableLiveData<RegisterResponse?>()
    val loginResponse = MutableLiveData<LoginResponse?>()
    private lateinit var loginSession: LoginSession
    private lateinit var mSessionPreference: SessionPreference

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

    fun updateData(userId: String, name: String, password: String) {
        loginSession = LoginSession()
        val updateCall = ApiConfig.getApiService().updateData(userId, name, password)

        Log.d("apa ya", "$userId, $name, $password")
        updateCall.enqueue(object : Callback<UpdateResponse> {
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>
            ) {
                val responseStatus = response.code()
                Log.d(".ProfileFragment", "Response status: $responseStatus")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        Log.d(".ProfileFragment", "Data updated successfully: $responseBody")

                        val currentLoginResponse = loginResponse.value
                        if (currentLoginResponse != null) {
                            val currentLoginResult = currentLoginResponse.signinResult
                            val updatedLoginResult = currentLoginResult.copy(name = name)
                            val updatedLoginResponse =
                                currentLoginResponse.copy(signinResult = updatedLoginResult)
                            loginResponse.postValue(updatedLoginResponse)
                        }

                    } else {
                        Log.e(".ProfileFragment", "Failed to update data: ${responseBody?.message}")
                    }
                } else {
                    Log.e(".ProfileFragment", "Failed to update data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                Log.e(".ProfileFragment", "Update data failed", t)
            }
        })
    }

    fun getMood(loginSession: LoginSession): LiveData<PagingData<moodResult>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                MoodPagingSource(apiService, loginSession)
            }
        ).liveData
    }

    fun setPrediction(loginSession: LoginSession){
        Log.d("tokenpredict", "${loginSession.token}")
        val prediction = ApiConfig.getApiService().getPredict("Bearer ${loginSession.token}", null, null)
        prediction.enqueue(object : Callback<MoodResponse> {
            override fun onResponse(
                call: Call<MoodResponse>,
                response: Response<MoodResponse>
            ) {
                Log.d("response", "$response")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        reportData.postValue(responseBody.moodResult)
                        Log.d("ReportFragment", "Berhasil woi")
                    }
                }else{
                    Log.d("ReportFragment", "Gagal woi")
                }
            }
            override fun onFailure(call: Call<MoodResponse>, t: Throwable) {
                Log.d("ReportFragment", "Failure woi")
                Log.d("response", "$t")
            }
        })
    }

    fun getPrediction(): LiveData<List<moodResult>>{
        return reportData
    }

    companion object {
        @Volatile
        private var instance: KumaRepository? = null
        fun getInstace(apiService: ApiService): KumaRepository = instance ?: synchronized(this){
            instance ?: KumaRepository(apiService)
        }.also { instance= it }
    }
}