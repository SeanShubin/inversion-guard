package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.jvmspec.analysis.statistics.Stats

interface StatsSummarizer {
    fun summarize(stats: Stats): List<Command>
}
