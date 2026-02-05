package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.analysis.InvocationType
import com.seanshubin.inversion.guard.appconfig.ErrorCountHolder
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CreateJsonFileCommand
import java.nio.file.Path

class QualityMetricsSummarizerImpl(
    private val outputDir: Path,
    private val errorCountHolder: ErrorCountHolder
) : QualityMetricsSummarizer {
    override fun summarize(analysisList: List<ClassAnalysis>): List<Command> {
        val count = countBoundaryInvocationsInNonBoundaryMethods(analysisList)
        errorCountHolder.errorCount = count
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
