package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.reporting.QualityMetrics
import java.nio.file.Path

interface Notifications {
    fun timeTakenMillis(millis: Long)
    fun executingCommand(command: Command)
    fun summaryEvent(metrics: QualityMetrics, maximumAllowedErrorCount: Int, outputDir: Path)
}
