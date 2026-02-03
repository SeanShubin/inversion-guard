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

interface Report {
    fun reportCommands(baseFileName: String, outputDir: Path, classFile: JvmClass): List<Command>
}
