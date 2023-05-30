package com.capstone.kuma.layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Spinner
import com.capstone.kuma.R
import com.capstone.kuma.databinding.ActivityCheckInBinding

class CheckInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckInBinding
    private lateinit var list_activity: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val list_submood = binding.subMood
        val dataSubMood = arrayListOf("angry","happy","sad","b aja")
        val adapterSubMood = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, dataSubMood)
        list_submood.setAdapter(adapterSubMood)
        list_submood.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())


        list_activity = binding.listActivity
        val activity = arrayOf("Reading and Learning","Spiritual", "Berak")
        val adapterActivity = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, activity)

        list_activity.adapter = adapterActivity
    }
}