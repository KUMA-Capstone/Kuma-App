package com.capstone.kuma.layout.ui.report

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.capstone.kuma.LoginSession
import com.capstone.kuma.R
import com.capstone.kuma.SessionPreference
import com.capstone.kuma.ViewModelFactory
import com.capstone.kuma.api.ApiConfig
import com.capstone.kuma.api.MoodResponse
import com.capstone.kuma.api.UploadResponse
import com.capstone.kuma.api.moodResult
import com.capstone.kuma.databinding.FragmentReportBinding
import com.capstone.kuma.layout.HomeActivity
import com.capstone.kuma.layout.ui.check_in.CheckInActivity
import com.capstone.kuma.repo.KumaRepository
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class ReportFragment : Fragment() {

    private lateinit var _binding: FragmentReportBinding
    private lateinit var mSessionPreference: SessionPreference
    private lateinit var factory: ViewModelFactory
    private val mReportViewModel: ReportViewModel by viewModels{
        factory
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSessionPreference = SessionPreference(context)
        factory  = ViewModelFactory.getInstance(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textReport
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        showLoading(true)

        val barchart: BarChart = binding.barchart

        /**val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = calendar.time
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = calendar.time

        val diff = TimeUnit.DAYS.convert(endDate.time - startDate.time, TimeUnit.MILLISECONDS)

        val dates = ArrayList<Date>()
        dates.add(startDate)
        for (i in 1..diff) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            dates.add(calendar.time)
        }**/
        mSessionPreference = SessionPreference(requireContext())


        mReportViewModel.getPrediction(mSessionPreference.getSession()).observe(requireActivity()) {
            val barentries = ArrayList<BarEntry>()
            val xAxis: XAxis = barchart.xAxis
            barchart.description.isEnabled = false
            xAxis.setDrawLabels(false)
            for (moodResult in it) {
                val datenya = moodResult.date.substring(8).toFloat()
                Log.d("hasil mood", "$datenya")
                barentries.add(BarEntry(datenya, moodResult.prediction))
            }
            val bardataSet = BarDataSet(barentries, "Anxiety Level")
            bardataSet.setColor(Color.rgb(46, 196, 182))
            val barData = BarData(bardataSet)
            barchart.data = barData
            barchart.invalidate()
            showLoading(false)
        }

        return root

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        }else{
            binding.loadingBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    companion object{
        const val LOGIN_SESSION = "login_session"
    }
}