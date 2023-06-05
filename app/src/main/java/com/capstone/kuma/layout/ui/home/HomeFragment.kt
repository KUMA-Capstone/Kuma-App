package com.capstone.kuma.layout.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.kuma.SessionPreference
import com.capstone.kuma.databinding.FragmentHomeBinding
import com.capstone.kuma.layout.CheckInActivity
import com.capstone.kuma.layout.TipsActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mSessionPreference: SessionPreference

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
            val intent = Intent(requireActivity(), TipsActivity::class.java)
            startActivity(intent)
        }

        mSessionPreference = SessionPreference(requireContext())
        binding.name.text = mSessionPreference.getSession().name
        Log.d("aaa", "nama : ${mSessionPreference.getSession().name}")

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