package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.analysis.InvocationType
import com.seanshubin.inversion.guard.analysis.MethodAnalysis
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CreateTextFileCommand
import com.seanshubin.inversion.guard.command.CreateJsonFileCommand
import com.seanshubin.inversion.guard.command.CreateFileCommand

import java.nio.file.Path

class QualityMetricsSummarizerImpl(
    private val outputDir: Path
) : QualityMetricsSummarizer {
    override fun summarize(analysisList: List<ClassAnalysis>): List<Command> {
        val count = countBoundaryInvocationsInNonBoundaryMethods(analysisList)
        val metrics = QualityMetrics(
            staticInvocationsThatShouldBeInverted = count
        )
        val path = outputDir.resolve(ReportCategory.COUNT.directory).resolve("quality-metrics.json")
        val command = CreateJsonFileCommand(path, metrics)
        return listOf(command)
    }

    private fun countBoundaryInvocationsInNonBoundaryMethods(
        analysisList: List<ClassAnalysis>
    ): Int {
        return analysisList.sumOf { classAnalysis ->
            classAnalysis.methodAnalysisList
                .filter { !it.isBoundaryLogic() }
                .sumOf { methodAnalysis ->
                    methodAnalysis.staticInvocations.count { invocation ->
                        invocation.invocationType == InvocationType.BOUNDARY
                    }
                }
        }
    }
}
