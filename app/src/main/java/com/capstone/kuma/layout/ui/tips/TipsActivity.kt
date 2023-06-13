package com.capstone.kuma.layout.ui.tips

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.kuma.R
import com.capstone.kuma.data.TipsItem
import com.capstone.kuma.data.adapter.ListTipsAdapter
import com.capstone.kuma.databinding.ActivityTipsBinding

class TipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipsBinding
    private lateinit var rvTips: RecyclerView
    private val list = ArrayList<TipsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tips"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_white)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)

        rvTips = findViewById(R.id.ListTips)
        rvTips.setHasFixedSize(true)

        list.addAll(getListTips())
        showRecyclerList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("Recycle")
    private fun getListTips(): ArrayList<TipsItem> {
        val dataImg = resources.obtainTypedArray(R.array.tips_img)
        val dataTitle = resources.getStringArray(R.array.tips_title)
        val dataDesc = resources.getStringArray(R.array.tips_desc)
        val listTips = ArrayList<TipsItem>()

        for (i in dataTitle.indices) {
            val tips = TipsItem(dataImg.getResourceId(i, -1), dataTitle[i], dataDesc[i])
            listTips.add(tips)
        }
        return listTips
    }

    private fun showRecyclerList() {
        rvTips.layoutManager = LinearLayoutManager(this)
        val listTipsAdapter = ListTipsAdapter(list)
        rvTips.adapter = listTipsAdapter
    }
}