package com.capstone.kuma.layout.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.capstone.kuma.custom.ButtonPrimary
import com.capstone.kuma.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var buttonPrimary: ButtonPrimary

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        nameEditText = binding.nameChange
        passwordEditText = binding.password
        buttonPrimary = binding.loginButton

        setupButtonPrimary()

        return root
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
