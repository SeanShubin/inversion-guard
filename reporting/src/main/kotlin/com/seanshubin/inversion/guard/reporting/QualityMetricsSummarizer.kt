package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysisSummary
import com.seanshubin.inversion.guard.command.Command

interface QualityMetricsSummarizer {
    fun summarize(analysisList: List<ClassAnalysisSummary>): List<Command>
}
