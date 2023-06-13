package com.capstone.kuma.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.kuma.api.LoginResponse
import com.capstone.kuma.api.RegisterResponse
import com.capstone.kuma.repo.KumaRepository

class AuthViewModel(private val kumaRepository: KumaRepository): ViewModel() {
    fun registerLauncher(name:String, email: String, password:String): LiveData<RegisterResponse?> {
        kumaRepository.registerLauncherRepo(name, email, password)
        return kumaRepository.getRegisterResponse()
    }

    fun loginLauncher(email: String, password:String) : LiveData<LoginResponse?> {
        kumaRepository.loginLauncherRepo(email,password)
        return kumaRepository.getLoginResponse()
    }
}