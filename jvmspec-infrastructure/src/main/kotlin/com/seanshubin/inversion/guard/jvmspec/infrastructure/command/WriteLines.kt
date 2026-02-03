package com.seanshubin.inversion.guard.jvmspec.infrastructure.command

import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: infrastructure
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class WriteLines(
    val path: Path,
    val lines: List<String>
) : Command {
    override fun execute(environment: Environment) {
        environment.files.write(path, lines)
    }
}
