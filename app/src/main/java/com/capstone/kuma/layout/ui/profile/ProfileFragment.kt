package com.capstone.kuma.layout.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.kuma.LoginSession
import com.capstone.kuma.SessionPreference
import com.capstone.kuma.api.ApiConfig
import com.capstone.kuma.auth.LoginActivity
import com.capstone.kuma.custom.ButtonPrimary
import com.capstone.kuma.databinding.FragmentProfileBinding
import com.capstone.kuma.layout.HomeActivity
import com.capstone.kuma.repo.KumaRepository

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var buttonPrimary: ButtonPrimary

    private lateinit var mSessionPreference: SessionPreference
    private lateinit var kumaRepository: KumaRepository
    private lateinit var loginSession: LoginSession

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        nameEditText = binding.nameChange
        passwordEditText = binding.password
        buttonPrimary = binding.updateButton

        setupButtonPrimary()

        loginSession = LoginSession()


        val editName: Editable? = mSessionPreference.getSession().name.let { Editable.Factory.getInstance().newEditable(it) }
        binding.nameChange.text = editName

        binding.report.setOnClickListener { view ->
            val email = "c23.ps366@gmail.com"
            val subject = "Hi, I got a problem"

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
            }

            if (intent.resolveActivity(view.context.packageManager) != null) {
                view.context.startActivity(intent)
            } else {
                Toast.makeText(view.context, "No email app installed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.logout.setOnClickListener {
            logOut()
        }

        buttonPrimary.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val userId = mSessionPreference.getSession().userId

            if (userId != null) {
                kumaRepository.updateData(userId, name, password)
                Toast.makeText(requireContext(), "User updated successfully", Toast.LENGTH_SHORT).show()
                mSessionPreference = SessionPreference(requireContext())
                if(mSessionPreference.getSession().name != ""){
                    mSessionPreference.getSession().token?.let { it1 ->
                        saveSession(userId, name,
                            it1
                        )
                    }
                    val newLoginSession = mSessionPreference.getSession()
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    intent.putExtra(HomeActivity.LOGIN_SESSION, newLoginSession)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(requireContext(), "User update failed", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun saveSession(userId: String, name: String, token: String) {
        mSessionPreference = SessionPreference(requireContext())

        loginSession = LoginSession()
        loginSession.userId = userId
        loginSession.name = name
        loginSession.token = token

        mSessionPreference.setSession(loginSession)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSessionPreference = SessionPreference(context)
        kumaRepository = KumaRepository.getInstace(ApiConfig.getApiService())
    }

    private fun logOut() {
        mSessionPreference.deleteSession()
        Log.d(".HomeActivity", "lihat : ${mSessionPreference.getSession()}")
        Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()
        val moveToLogin = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(moveToLogin)
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

        nameEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        updateButtonPrimaryState()
    }

    private fun updateButtonPrimaryState() {
        val name = nameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        val isButtonEnabled = name.isNotEmpty() && password.length >= 8
        buttonPrimary.isEnabled = isButtonEnabled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
