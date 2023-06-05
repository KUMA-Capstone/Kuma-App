package com.capstone.kuma.layout.ui.check_in

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.MultiAutoCompleteTextView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.capstone.kuma.LoginSession
import com.capstone.kuma.R
import com.capstone.kuma.api.ApiConfig
import com.capstone.kuma.api.UploadResponse
import com.capstone.kuma.custom.ButtonPrimary
import com.capstone.kuma.databinding.ActivityCheckInBinding
import com.capstone.kuma.layout.HomeActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CheckInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckInBinding
    private lateinit var list_activity: Spinner

    private lateinit var submood: MultiAutoCompleteTextView
    private lateinit var activities: Spinner
    private lateinit var story: EditText
    private lateinit var buttonPrimary: ButtonPrimary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Check In"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_white)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)

        val list_submood = binding.subMood
        val dataSubMood = arrayListOf("angry","happy","sad","b aja")
        val adapterSubMood = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, dataSubMood)
        list_submood.setAdapter(adapterSubMood)
        list_submood.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        list_activity = binding.listActivity
        val activity = arrayOf("Reading and Learning","Spiritual", "Berak")
        val adapterActivity = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, activity)
        list_activity.adapter = adapterActivity

        submood = binding.subMood
        activities = binding.listActivity
        story = binding.story
        buttonPrimary = binding.finishCheckin

        setupButtonPrimary()

        val loginSession = intent.getParcelableExtra<LoginSession>(LOGIN_SESSION) as LoginSession

        binding.finishCheckin.setOnClickListener {startUpload(loginSession)}
    }

    private fun startUpload(loginSession: LoginSession){
//        val currentDate = Calendar.getInstance().time
//        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//        val dateNow = dateFormat.parse(currentDate.toString())
        val dateNow = Date().toString()
        val sub_mood = binding.subMood.text.toString().toRequestBody("text/plain".toMediaType())
        val listActivity = binding.listActivity.onItemSelectedListener.toString().toRequestBody("text/plain".toMediaType())
        val story = binding.story.text.toString().toRequestBody("text/plain".toMediaType())

        val uploadCheckIn = ApiConfig.getApiService().upload("Bearer ${loginSession.token}", dateNow, sub_mood, listActivity, story)

        Log.d("isi upload", "${loginSession.token}, ${loginSession.userId}, $dateNow, $sub_mood, $listActivity, $story")
        uploadCheckIn.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                Log.d("response nya", "$response")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showLoading(false)
                        Toast.makeText(this@CheckInActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@CheckInActivity, HomeActivity::class.java)
                        Log.d("CheckIn", "Berhasil woi")
                        intent.putExtra(HomeActivity.LOGIN_SESSION, loginSession)
                        startActivity(intent)
                        finish()
                    }
                }else{
                    showLoading(false)
                    Log.d("CheckIn", "Gagal woi")
                    Toast.makeText(this@CheckInActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                showLoading(false)
                Log.d("CheckIn", "Failure woi")
                Toast.makeText(this@CheckInActivity, "Error when Check In", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupButtonPrimary() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing.
            }

            override fun afterTextChanged(s: Editable?) {
                updateButtonPrimaryState()
            }
        }

        submood.addTextChangedListener(textWatcher)
        story.addTextChangedListener(textWatcher)

        updateButtonPrimaryState()
    }

    private fun updateButtonPrimaryState() {
        val submood = submood.text.toString().trim()
        val activities = activities.onItemSelectedListener.toString().trim()
        val story = story.text.toString().trim()

        val isButtonEnabled = submood.isNotEmpty() && activities.isNotEmpty() && story.isNotEmpty()
        buttonPrimary.isEnabled = isButtonEnabled
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        }else{
            binding.loadingBar.visibility = View.GONE
        }
    }

    companion object{
        const val LOGIN_SESSION = "login_session"
    }
}