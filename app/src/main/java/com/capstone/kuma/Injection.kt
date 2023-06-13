package com.capstone.kuma

import android.content.Context
import com.capstone.kuma.api.ApiConfig
import com.capstone.kuma.repo.KumaRepository

object Injection {
    fun provideRepository(context: Context): KumaRepository {
        val apiService = ApiConfig.getApiService()
        return KumaRepository.getInstace(apiService)
    }
}