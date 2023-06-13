package com.capstone.kuma.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.kuma.LoginSession
import com.capstone.kuma.SessionPreference
import com.capstone.kuma.ViewModelFactory
import com.capstone.kuma.databinding.ActivityLoginBinding
import com.capstone.kuma.layout.HomeActivity

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val authViewModel: AuthViewModel by viewModels{
        factory
    }
    private lateinit var mSessionPreference: SessionPreference
    private lateinit var loginSession: LoginSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFullScreen()

        mSessionPreference = SessionPreference(this)
        if(mSessionPreference.getSession().name != ""){
            val newLoginSession = mSessionPreference.getSession()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(HomeActivity.LOGIN_SESSION, newLoginSession)
            startActivity(intent)
            finish()
        }

        binding.loginButton.isEnabled = false
        binding.email.addTextChangedListener(textWatcher)
        binding.password.addTextChangedListener(textWatcher)

        binding.loginButton.setOnClickListener {
            showLoading(true)
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            authViewModel.loginLauncher(email, password).observe(this) {
                if (it != null) {
                    if (it.error == true) {
                        showLoading(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    } else {
                        saveSession(
                            it.signinResult.userId,
                            it.signinResult.name,
                            it.signinResult.token
                        )
                    }
                } else {
                    showLoading(false)
                    Toast.makeText(
                        this,
                        "Login failed, Please check the data again",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.toRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveSession(userId: String, name: String, token: String) {
        mSessionPreference = SessionPreference(this)

        loginSession = LoginSession()
        loginSession.userId = userId
        loginSession.name = name
        loginSession.token = token

        mSessionPreference.setSession(loginSession)
        moveToHome()
    }

    private fun moveToHome() {
        mSessionPreference = SessionPreference(this)
        val newLoginSession = mSessionPreference.getSession()
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        Toast.makeText(this, "Welcome ${newLoginSession.name}", Toast.LENGTH_SHORT).show()
        intent.putExtra(HomeActivity.LOGIN_SESSION, newLoginSession)
        startActivity(intent)
        finish()
    }

    private fun setFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val isPasswordValid = password.length >= 8
            val isEmailValid = isValidEmail(email)
            val isInputValid = isEmailValid && isPasswordValid

            binding.loginButton.isEnabled = isInputValid
        }

        override fun afterTextChanged(s: Editable?) {
            // Do nothing
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(email).matches()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        }else{
            binding.loadingBar.visibility = View.GONE
        }
    }
}