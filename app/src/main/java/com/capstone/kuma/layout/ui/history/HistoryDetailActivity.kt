package com.capstone.kuma.layout.ui.history

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.capstone.kuma.SessionPreference
import com.capstone.kuma.api.moodResult
import com.capstone.kuma.databinding.ActivityHistoryDetailBinding

@Suppress("DEPRECATION")
class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)
        setFullScreen()

        val history = intent.getParcelableExtra<moodResult>(OBJECT) as moodResult
        val sessionPreference = SessionPreference(this)
        binding.name.text = sessionPreference.getSession().name
        binding.date.text = history.date
        binding.submood.text = history.sub_mood
        binding.activity.text = history.activities
        binding.story.text = history.story
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        } else {
            binding.loadingBar.visibility = View.GONE
        }
    }

    private fun setFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    }

    companion object {
        const val OBJECT = "object"
    }
}