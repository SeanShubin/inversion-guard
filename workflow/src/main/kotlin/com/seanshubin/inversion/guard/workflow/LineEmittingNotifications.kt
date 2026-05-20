package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.duration.format.DurationFormat
import com.seanshubin.inversion.guard.reporting.QualityMetrics
import java.nio.file.Path

class LineEmittingNotifications(
    private val emit: (Any?) -> Unit
) : Notifications {
    override fun timeTakenMillis(millis: Long) {
        DurationFormat.milliseconds.format(millis).let { formattedDuration ->
            emit("Time taken: $formattedDuration")
        }
    }

    override fun executingCommand(command: Command) {}

    override fun summaryEvent(metrics: QualityMetrics, maximumAllowedErrorCount: Int, outputDir: Path) {
        emit("Should Be Inverted: ${metrics.staticInvocationsThatShouldBeInverted} (counted as errors)")
        emit("Acceptable: ${metrics.staticInvocationsThatAreAcceptable}")
        emit("Ignored: ${metrics.staticInvocationsThatAreIgnored}")
        emit("Should Be Classified: ${metrics.staticInvocationsThatShouldBeClassified}")
        emit("Total Errors: ${metrics.staticInvocationsThatShouldBeInverted} of $maximumAllowedErrorCount errors allowed")
        emit("Output: $outputDir")
    }
}
