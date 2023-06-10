package com.capstone.kuma.layout.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.kuma.LoginSession
import com.capstone.kuma.R
import com.capstone.kuma.ViewModelFactory
import com.capstone.kuma.data.adapter.MoodAdapter
import com.capstone.kuma.databinding.ActivityHistoryBinding

@Suppress("DEPRECATION")
class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val mHistoryViewModel: HistoryViewModel by viewModels{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_white)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)

        val loginSession = intent.getParcelableExtra<LoginSession>(LOGIN_SESSION) as LoginSession

        LinearLayoutManager(this)
        binding.listMood.layoutManager = LinearLayoutManager(this)


        getMood(loginSession)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getMood(loginSession: LoginSession){
        showLoading(true)
        val adapter = MoodAdapter()
        binding.listMood.adapter = adapter
        mHistoryViewModel.getMoods(loginSession).observe(this) {
            adapter.submitData(lifecycle, it)
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

    companion object{
        const val LOGIN_SESSION="login_session"
    }
}