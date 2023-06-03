package com.capstone.kuma.layout.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.kuma.R
import com.capstone.kuma.databinding.FragmentReportBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        val barchart: BarChart = binding.barchart

        val calendar = Calendar.getInstance()
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
        }

        val barentries = arrayListOf<BarEntry>()
        for (i in dates.indices){
            val date  = dates[i]
            val value = barentries.add(BarEntry(i + 1f, 4f))
        }

        val bardataSet = BarDataSet(barentries, "Anxiety Level")

        val barData = BarData(bardataSet)
        barchart.data = barData
        barchart.invalidate()

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}