package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.analysis.ClassAnalyzer
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CommandRunner
import com.seanshubin.inversion.guard.command.CreateFileCommand
import com.seanshubin.inversion.guard.fileselection.FileSelector
import com.seanshubin.inversion.guard.reporting.QualityMetricsSummarizer
import com.seanshubin.inversion.guard.reporting.QualityMetricsDetailSummarizer
import com.seanshubin.inversion.guard.reporting.HtmlReportSummarizer
import com.seanshubin.inversion.guard.reporting.HtmlStatsSummarizer

interface Notifications {
    fun timeTakenMillis(millis: Long)
    fun executingCommand(command: Command)
}
