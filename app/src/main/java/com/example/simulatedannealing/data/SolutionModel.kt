package com.example.simulatedannealing.data

data class SolutionModel(
        var plan: MutableList<Int> = mutableListOf(),
        var energy: Int
)
