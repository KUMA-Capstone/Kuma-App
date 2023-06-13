package com.capstone.kuma.layout.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.capstone.kuma.LoginSession
import com.capstone.kuma.api.moodResult
import com.capstone.kuma.repo.KumaRepository

class HistoryViewModel(private val kumaRepository: KumaRepository): ViewModel() {
    fun getMoods(loginSession: LoginSession): LiveData<PagingData<moodResult>>{
        return kumaRepository.getMood(loginSession)
    }
}