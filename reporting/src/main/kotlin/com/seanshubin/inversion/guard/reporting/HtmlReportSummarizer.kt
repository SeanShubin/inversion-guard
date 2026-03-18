package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.analysis.ClassAnalysisSummary
import com.seanshubin.inversion.guard.command.Command

interface HtmlReportSummarizer {
    fun summarize(analysisList: List<ClassAnalysisSummary>): List<Command>
    fun generateClassPage(analysis: ClassAnalysis): Command
}
