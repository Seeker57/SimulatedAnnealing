package com.example.simulatedannealing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simulatedannealing.MainActivity
import com.example.simulatedannealing.R
import com.example.simulatedannealing.data.AnnealingServiceImpl
import kotlinx.android.synthetic.main.fragment_annealing.*
import kotlinx.android.synthetic.main.fragment_main_window.*
import kotlinx.android.synthetic.main.input_line.view.*


class MainWindowFragment : AbstractFragment(R.layout.fragment_main_window) {

    override fun setActionBar() {
        val actionBar = (activity as MainActivity?)!!.supportActionBar
        actionBar?.show()
        actionBar?.title = getString(R.string.russian_app_name)
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main_window, container, false)
        setActionBar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setElementsUI(view)
    }


    override fun setElementsUI(view: View) {
        lineMinTemp.setTitleText(getString(R.string.min_temp))
        lineMaxTemp.setTitleText(getString(R.string.max_temp))
        lineCoefficientTemp.setTitleText(getString(R.string.coefficient_dec_temp))
        lineQueenCount.setTitleText(getString(R.string.queen_count))
        lineStepCount.setTitleText(getString(R.string.iteration_count))

        calcButton.setOnClickListener {
            if (inputIsEmpty()) {
                showMessage(getString(R.string.error), getString(R.string.fill_all_fields))
                return@setOnClickListener
            }

            (activity as MainActivity).showProgress()
            fragmentListener.changeFragment(AnnealingFragment(
                lineMinTemp.getEditText().toFloat(), lineMaxTemp.getEditText().toFloat(),
                lineCoefficientTemp.getEditText().toFloat(), lineQueenCount.getEditText().toInt(),
                lineStepCount.getEditText().toInt(), AnnealingServiceImpl()
            ), R.string.annealing_window)
        }
    }

    private fun inputIsEmpty(): Boolean {
        return lineMinTemp.getEditText().isEmpty() ||
               lineMaxTemp.getEditText().isEmpty() ||
               lineCoefficientTemp.getEditText().isEmpty() ||
               lineQueenCount.getEditText().isEmpty() ||
               lineStepCount.getEditText().isEmpty()
    }
}