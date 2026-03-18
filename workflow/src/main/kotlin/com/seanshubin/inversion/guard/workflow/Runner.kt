package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.analysis.ClassAnalysisSummary
import com.seanshubin.inversion.guard.analysis.ClassAnalyzer
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CommandRunner
import com.seanshubin.inversion.guard.command.CreateFileCommand
import com.seanshubin.inversion.guard.fileselection.FileSelector
import com.seanshubin.inversion.guard.reporting.QualityMetricsSummarizer
import com.seanshubin.inversion.guard.reporting.QualityMetricsDetailSummarizer
import com.seanshubin.inversion.guard.reporting.HtmlReportSummarizer
import com.seanshubin.inversion.guard.reporting.HtmlStatsSummarizer

import com.seanshubin.inversion.guard.jvmspec.analysis.statistics.Stats
import com.seanshubin.inversion.guard.di.contract.FilesContract
import com.seanshubin.inversion.guard.jvmspec.infrastructure.time.Timer
import com.seanshubin.inversion.guard.jvmspec.model.conversion.Converter
import com.seanshubin.inversion.guard.runtime.ErrorCountHolder
import com.seanshubin.inversion.guard.workflow.ConfigDocumentation.maximumAllowedErrorCount

class Runner(
    private val files: FilesContract,
    private val fileSelector: FileSelector,
    private val classAnalyzer: ClassAnalyzer,
    private val qualityMetricsSummarizer: QualityMetricsSummarizer,
    private val qualityMetricsDetailSummarizer: QualityMetricsDetailSummarizer,
    private val htmlReportSummarizer: HtmlReportSummarizer,
    private val htmlStatsSummarizer: HtmlStatsSummarizer,
    private val stats: Stats,
    private val classProcessor: ClassProcessor,
    private val commandRunner: CommandRunner,
    private val timer: Timer,
    private val timeTakenEvent: (Long) -> Unit,
    private val converter: Converter,
    private val errorCountHolder: ErrorCountHolder,
    private val setExitCode: (Int) -> Unit,
    private val maximumAllowedErrorCount: Int
) : Runnable {
    override fun run() {
        val durationMillis = timer.withTimerMilliseconds {
            val analysisList = mutableListOf<ClassAnalysisSummary>()

            // Process and write each class immediately to allow garbage collection
            fileSelector.map { file ->
                try {
                    val jvmClass = with(converter) { file.toJvmClass(files, file) }
                    val analysis = classAnalyzer.analyzeClass(jvmClass)

                    // Immediately create and write this class's disassembly and HTML page
                    val classCommands = classProcessor.processClass(analysis)
                    classCommands.forEach { command ->
                        commandRunner.runCommand(command)
                    }

                    val htmlClassPageCommand = htmlReportSummarizer.generateClassPage(analysis)
                    commandRunner.runCommand(htmlClassPageCommand)

                    // Convert to lightweight summary and discard heavy JvmClass reference
                    val summary = ClassAnalysisSummary.fromClassAnalysis(analysis)
                    analysisList.add(summary)
                } catch(ex:Exception) {
                    throw RuntimeException("Error processing file ${file.toAbsolutePath()}", ex)
                }
            }

            // Generate and write summary reports
            val summaryCommands = qualityMetricsSummarizer.summarize(
                analysisList
            ) + qualityMetricsDetailSummarizer.summarize(analysisList) + htmlReportSummarizer.summarize(analysisList) + htmlStatsSummarizer.summarize(
                stats
            )
            summaryCommands.forEach { command ->
                commandRunner.runCommand(command)
            }
        }
        timeTakenEvent(durationMillis)
        val exitCode = if (errorCountHolder.errorCount > maximumAllowedErrorCount) 1 else 0
        setExitCode(exitCode)
    }
}
