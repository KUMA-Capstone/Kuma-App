package com.capstone.kuma.layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.capstone.kuma.R
import com.capstone.kuma.databinding.ActivityPanicBinding

class PanicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPanicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.panicButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivity(intent)
        }
    }

    companion object {
        private const val PICK_CONTACT_REQUEST_CODE = 1
    }
}