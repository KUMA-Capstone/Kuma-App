package com.capstone.kuma.layout.ui.report

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.kuma.SessionPreference
import com.capstone.kuma.ViewModelFactory
import com.capstone.kuma.databinding.FragmentReportBinding
import com.capstone.kuma.layout.ui.report.ReportFragment.Global.averagePrediction
import com.capstone.kuma.layout.ui.report.ReportFragment.Global.predictionCount
import com.capstone.kuma.layout.ui.report.ReportFragment.Global.totalPrediction
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ReportFragment : Fragment() {

    private lateinit var _binding: FragmentReportBinding
    private lateinit var mSessionPreference: SessionPreference
    private lateinit var factory: ViewModelFactory
    private val mReportViewModel: ReportViewModel by viewModels{
        factory
    }
    object Global{
        var averagePrediction: Float = 0.0f
        var predictionCount: Int = 0
        var totalPrediction: Float = 0.0f
        var isAverageCalculated: Boolean = false
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSessionPreference = SessionPreference(context)
        factory  = ViewModelFactory.getInstance(context)
    }

    @SuppressLint("SetTextI18n")
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

                if (!Global.isAverageCalculated) {
                    totalPrediction += moodResult.prediction
                }
            }
            if (it.isNotEmpty()) {
                val lastMoodResult = it[it.size - 1]
                val lastPrediction = lastMoodResult.prediction
                binding.prediction.text = lastPrediction.toString()
                if(lastPrediction<1.0){
                    binding.moodlevel.text = "Very Poor"
                }else if(lastPrediction<2.0){
                    binding.moodlevel.text = "Poor"
                }else if(lastPrediction<3.0){
                    binding.moodlevel.text = "Neutral"
                }else if(lastPrediction<4.0){
                    binding.moodlevel.text = "Good"
                }else if(lastPrediction<5.0){
                    binding.moodlevel.text = "Very Good"
                }
            }

            predictionCount = it.size
            averagePrediction = totalPrediction / predictionCount

            Global.isAverageCalculated = true

            val bardataSet = BarDataSet(barentries, "Mood Level")
            bardataSet.color = Color.rgb(46, 196, 182)
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
}