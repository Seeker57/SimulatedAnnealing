package com.example.simulatedannealing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simulatedannealing.MainActivity
import com.example.simulatedannealing.R
import com.example.simulatedannealing.data.AnnealingService
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_annealing.*
import android.app.Activity
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import com.github.mikephil.charting.charts.LineChart


class AnnealingFragment(
        private val minTemp: Float, private val maxTemp: Float, private val tempCoefficient: Float,
        private val queenCount: Int, private val stepCount: Int, private val annealingService: AnnealingService
) : AbstractFragment(R.layout.fragment_annealing) {

    override fun setActionBar() {
        val actionBar = (activity as MainActivity?)!!.supportActionBar
        actionBar?.show()
        actionBar?.title = getString(R.string.russian_app_name)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_annealing, container, false)
        setActionBar()
        hideSoftKeyboard(activity as MainActivity)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setElementsUI(view)
    }

    override fun setElementsUI(view: View) {
        val tempLine = mutableListOf<Entry>()
        val energyLine = mutableListOf<Entry>()
        val badSolutionLine = mutableListOf<Entry>()

        result.text = annealingService.start(
                minTemp, maxTemp, tempCoefficient, queenCount,
                stepCount, tempLine, energyLine, badSolutionLine)
        (activity as MainActivity).hideProgress()

        prepareChart(chartTemp, tempLine, getString(R.string.line_title_temp), Color.rgb(153, 0, 31))
        prepareChart(chartEnergy, energyLine, getString(R.string.line_title_energy), Color.BLUE)
        prepareChart(chartBadSolution, badSolutionLine, getString(R.string.line_title_bad_solution), Color.rgb(39, 163, 39))
    }

    private fun hideSoftKeyboard(activity: Activity){
        val inputMethodManager: InputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isAcceptingText){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    0
            )
        }
    }

    private fun prepareChart(chart: LineChart, dataLine: MutableList<Entry>, lineTitle: String, lineColor: Int) {
        val dataSetLine = LineDataSet(dataLine.toList(), lineTitle)
        dataSetLine.setColor(lineColor, 1000)
        dataSetLine.setDrawCircles(false)
        dataSetLine.lineWidth = 3.0f

        val lineData = LineData(dataSetLine)
        chart.data = lineData
        chart.setTouchEnabled(false)

        val newDesc = Description()
        newDesc.text = ""
        chart.description = newDesc
        chart.invalidate()
    }
}