package com.seanshubin.inversion.guard.jvmspec.runtime.application

import com.seanshubin.inversion.guard.jvmspec.infrastructure.command.Command
import com.seanshubin.inversion.guard.jvmspec.infrastructure.command.CommandRunner
import com.seanshubin.inversion.guard.jvmspec.infrastructure.time.DurationFormat
import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class LineEmittingNotifications(
    val emit: (Any?) -> Unit,
    val commandRunner: CommandRunner
) : Notifications {
    override fun processingFile(inputFile: Path, outputDir: Path) {
        emit("$inputFile -> $outputDir")
    }

    override fun timeTakenMillis(millis: Long) {
        DurationFormat.milliseconds.format(millis).let { formattedDuration ->
            emit("Time taken: $formattedDuration")
        }
    }

    override fun executeCommand(command: Command) {
        commandRunner.run(command)
    }
}
