package com.capstone.kuma.layout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.kuma.LoginSession
import com.capstone.kuma.R
import com.capstone.kuma.SessionPreference
import com.capstone.kuma.ViewModelFactory
import com.capstone.kuma.auth.LoginActivity
import com.capstone.kuma.databinding.ActivityHomeBinding
import com.capstone.kuma.layout.ui.check_in.CheckInActivity.Companion.LOGIN_SESSION
import com.capstone.kuma.layout.ui.home.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mSessionPreference: SessionPreference
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val mHomeViewModel: HomeViewModel by viewModels{
        factory
    }
    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        val loginSession = intent.getParcelableExtra<LoginSession>(LOGIN_SESSION) as LoginSession

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        setupWithNavController(bottomNavigationView, navController)
        navView.setupWithNavController(navController)
    }

    companion object{
        const val LOGIN_SESSION="extra_person"
    }
}