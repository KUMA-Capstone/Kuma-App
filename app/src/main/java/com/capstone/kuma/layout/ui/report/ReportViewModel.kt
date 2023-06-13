package com.capstone.kuma.layout.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.kuma.LoginSession
import com.capstone.kuma.api.moodResult
import com.capstone.kuma.repo.KumaRepository

class ReportViewModel(private val kumaRepository: KumaRepository) : ViewModel() {
    fun getPrediction(loginSession: LoginSession): LiveData<List<moodResult>>{
        kumaRepository.setPrediction(loginSession)
        return kumaRepository.getPrediction()
    }
}