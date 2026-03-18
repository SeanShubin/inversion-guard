package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysisSummary
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CreateJsonFileCommand
import java.nio.file.Path

class QualityMetricsDetailSummarizerImpl(
    private val outputDir: Path,
    private val reportGenerator: QualityMetricsDetailReportGenerator
) : QualityMetricsDetailSummarizer {
    override fun summarize(analysisList: List<ClassAnalysisSummary>): List<Command> {
        val report = reportGenerator.generate(analysisList)
        val path = outputDir.resolve(ReportCategory.DIFF.directory)
            .resolve("quality-metrics-staticInvocationsThatShouldBeInverted.json")
        val command = CreateJsonFileCommand(path, report)
        return listOf(command)
    }
}
