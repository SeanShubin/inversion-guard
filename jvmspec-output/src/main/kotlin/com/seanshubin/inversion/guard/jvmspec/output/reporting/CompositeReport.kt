package com.seanshubin.inversion.guard.jvmspec.output.reporting

import com.seanshubin.inversion.guard.jvmspec.infrastructure.command.Command
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: output
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class CompositeReport(val reports: List<Report>) : Report {
    override fun reportCommands(
        baseFileName: String,
        outputDir: Path,
        classFile: JvmClass
    ): List<Command> {
        return reports.flatMap { it.reportCommands(baseFileName, outputDir, classFile) }
    }
}
