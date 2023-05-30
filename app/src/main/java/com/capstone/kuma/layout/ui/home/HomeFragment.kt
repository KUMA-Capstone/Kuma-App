package com.capstone.kuma.layout.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.kuma.databinding.FragmentHomeBinding
import com.capstone.kuma.layout.CheckInActivity
import com.capstone.kuma.layout.HomeActivity
import com.capstone.kuma.layout.PanicActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.panicButton.setOnClickListener {
            val intent = Intent(requireActivity(), PanicActivity::class.java)
            startActivity(intent)
        }

        binding.checkIn.setOnClickListener {
            val intent = Intent(requireActivity(), CheckInActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}