package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.analysis.InvocationType
import com.seanshubin.inversion.guard.analysis.MethodAnalysis
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CreateTextFileCommand
import com.seanshubin.inversion.guard.command.CreateJsonFileCommand
import com.seanshubin.inversion.guard.command.CreateFileCommand

interface QualityMetricsSummarizer {
    fun summarize(analysisList: List<ClassAnalysis>): List<Command>
}
