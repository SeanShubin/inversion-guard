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

import com.seanshubin.inversion.guard.jvmspec.rules.CategoryRule
import java.nio.file.Path

data class Configuration(
    val baseDir: Path,
    val outputDir: Path,
    val includeFile: List<String>,
    val excludeFile: List<String>,
    val skipDir: List<String>,
    val core: List<String>,
    val boundary: List<String>,
    val localCore: List<String>,
    val localBoundary: List<String>,
    val failOnUnknown: Boolean,
    val categoryRuleSet: Map<String, CategoryRule>,
    val maximumAllowedErrorCount: Int
)
