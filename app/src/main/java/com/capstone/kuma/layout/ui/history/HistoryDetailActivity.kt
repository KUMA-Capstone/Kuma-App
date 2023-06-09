package com.capstone.kuma.layout.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.kuma.R
import com.capstone.kuma.api.moodResult
import com.capstone.kuma.databinding.ActivityHistoryBinding
import com.capstone.kuma.databinding.ActivityHistoryDetailBinding

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        supportActionBar?.title = "Detail History"

        val history = intent.getParcelableExtra<moodResult>(OBJECT) as moodResult
        with(binding){
            date.text = history.date
            subMood.text = history.sub_mood
        }
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        }else{
            binding.loadingBar.visibility = View.GONE
        }
    }

    companion object {
        const val OBJECT = "object"
    }
}