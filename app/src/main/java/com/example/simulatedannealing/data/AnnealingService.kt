package com.example.simulatedannealing.data

import com.github.mikephil.charting.data.Entry

interface AnnealingService {
    fun start(
            minTemp: Float, maxTemp: Float, tempCoefficient: Float,
            queenCount: Int, stepCount: Int, tempLine: MutableList<Entry>,
            energyLine: MutableList<Entry>, badSolutionLine: MutableList<Entry>
    ) : String
}