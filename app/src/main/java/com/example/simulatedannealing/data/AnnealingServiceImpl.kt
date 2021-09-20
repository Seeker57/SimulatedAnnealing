package com.example.simulatedannealing.data

import com.github.mikephil.charting.data.Entry
import java.util.*
import kotlin.math.exp

class AnnealingServiceImpl() : AnnealingService {

    private var queenCount: Int = 0

    override fun start(
            minTemp: Float, maxTemp: Float, tempCoefficient: Float,
            queenCount: Int, stepCount: Int, tempLine: MutableList<Entry>,
            energyLine: MutableList<Entry>, badSolutionLine: MutableList<Entry>
    ) : String {
        this.queenCount = queenCount

        val bestSolution = SolutionModel(energy = 100)
        val currentSolution = SolutionModel(energy = 0)
        val workingSolution = SolutionModel(energy = 0)

        var currentTemp = maxTemp
        var flagBest = false
        var time = 0

        initializeSolution(currentSolution)
        computeEnergy(currentSolution)
        copySolution(workingSolution, currentSolution)

        while (currentTemp > minTemp) {
            tempLine.add(Entry(time.toFloat(), currentTemp))

            var accepted = 0
            for (step in (0 until stepCount)) {
                var flagNew = false
                tweakSolution(workingSolution)
                computeEnergy(workingSolution)

                if (workingSolution.energy <= currentSolution.energy) {
                    flagNew = true
                } else {
                    val delta = workingSolution.energy - currentSolution.energy
                    val p = exp(-delta / currentTemp)
                    if (p > Math.random()) {
                        accepted += 1
                        flagNew = true
                    }
                }

                if (flagNew) {
                    flagNew = false
                    copySolution(currentSolution, workingSolution)
                    if (currentSolution.energy < bestSolution.energy) {
                        copySolution(bestSolution, currentSolution)
                        energyLine.add(Entry(time.toFloat(), bestSolution.energy.toFloat()))
                        flagBest = true
                    }
                } else {
                    copySolution(workingSolution, currentSolution)
                }
            }

            badSolutionLine.add(Entry(time.toFloat(), accepted.toFloat()))
            currentTemp *= tempCoefficient
            time += 1
        }

        return if (flagBest) {
            showSolution(bestSolution)
        } else {
            ""
        }
    }

    //region ==================== Internal ====================

    private fun tweakSolution(solution: SolutionModel) {
        val x = (0 until queenCount).random()
        var y = x
        while (x == y) {
            y = (0 until queenCount).random()
        }
        Collections.swap(solution.plan, x, y)
    }

    private fun initializeSolution(solution: SolutionModel) {
        for(i in (0 until queenCount)) {
            solution.plan.add(i)
        }

        for(i in (0 until queenCount)) {
            tweakSolution(solution)
        }
    }

    private fun computeEnergy(solution: SolutionModel) {
        val dx = listOf<Int>(-1, 1, -1, 1)
        val dy = listOf<Int>(-1, 1, 1, -1)
        var conflicts = 0

        for (x in (0 until queenCount)) {
            for (j in (0 until 4)) {
                var tx = x + dx[j]
                var ty = solution.plan[x] + dy[j]
                while ( (tx > 0) and (tx < queenCount) and (ty > 0) and (ty < queenCount) ) {
                    if (solution.plan[tx] == ty) {
                        conflicts += 1
                    }
                    tx += dx[j]
                    ty += dy[j]
                }

            }
        }
        solution.energy = conflicts
    }

    private fun copySolution(to: SolutionModel, from: SolutionModel) {
        to.plan.clear()
        for (i in (0 until queenCount)) {
            to.plan.add(from.plan[i])
        }
        to.energy = from.energy
    }

    private fun showSolution(solution: SolutionModel): String {
        var viewSolution = ""
        for (y in (0 until queenCount)) {
            for (x in (0 until queenCount)) {
                viewSolution += if (solution.plan[x] == y) {
                    " Q "
                } else {
                    " # "
                }
            }
            viewSolution += "\n"
        }

        return viewSolution
    }

    //endregion
}