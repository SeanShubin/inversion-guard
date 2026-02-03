package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.analysis.InvocationType
import com.seanshubin.inversion.guard.analysis.MethodAnalysis
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CreateTextFileCommand
import com.seanshubin.inversion.guard.command.CreateJsonFileCommand
import com.seanshubin.inversion.guard.command.CreateFileCommand

import com.seanshubin.inversion.guard.jvmspec.analysis.statistics.Stats

interface StatsSummarizer {
    fun summarize(stats: Stats): List<Command>
}
