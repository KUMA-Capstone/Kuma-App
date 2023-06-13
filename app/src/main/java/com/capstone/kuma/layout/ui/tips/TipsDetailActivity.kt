package com.capstone.kuma.layout.ui.tips

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.capstone.kuma.R
import com.capstone.kuma.databinding.ActivityTipsDetailBinding

class TipsDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTipsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tips"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_white)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)

        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")

        binding.TitleDetail.text = title
        binding.TitleDesc.text = desc
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}