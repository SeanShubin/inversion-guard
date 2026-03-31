package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysisSummary
import com.seanshubin.inversion.guard.analysis.QualityMetric
import com.seanshubin.inversion.guard.analysis.mapToQualityMetric
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CreateJsonFileCommand
import com.seanshubin.inversion.guard.runtime.ErrorCountHolder
import java.nio.file.Path

class QualityMetricsSummarizerImpl(
    private val outputDir: Path,
    private val errorCountHolder: ErrorCountHolder
) : QualityMetricsSummarizer {
    override fun summarize(analysisList: List<ClassAnalysisSummary>): List<Command> {
        val metricCounts = countByQualityMetric(analysisList)

        val inverted = metricCounts[QualityMetric.STATIC_INVOCATIONS_THAT_SHOULD_BE_INVERTED] ?: 0
        val acceptable = metricCounts[QualityMetric.STATIC_INVOCATIONS_THAT_ARE_ACCEPTABLE] ?: 0
        val unclassified = metricCounts[QualityMetric.STATIC_INVOCATIONS_THAT_SHOULD_BE_CLASSIFIED] ?: 0

        errorCountHolder.errorCount = inverted

        val metrics = QualityMetrics(
            staticInvocationsThatShouldBeInverted = inverted,
            staticInvocationsThatAreAcceptable = acceptable,
            staticInvocationsThatShouldBeClassified = unclassified
        )

        val path = outputDir.resolve(ReportCategory.COUNT.directory).resolve("quality-metrics.json")
        val command = CreateJsonFileCommand(path, metrics)
        return listOf(command)
    }

    private fun countByQualityMetric(
        analysisList: List<ClassAnalysisSummary>
    ): Map<QualityMetric, Int> {
        return analysisList
            .flatMap { it.methodAnalysisList }
            .filter { !it.isBoundaryLogic() }
            .flatMap { it.staticInvocations }
            .groupingBy { mapToQualityMetric(it.invocationTypes) }
            .eachCount()
    }
}
