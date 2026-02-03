package com.seanshubin.inversion.guard.command

import java.nio.file.Path

data class CreateTextFileCommand(
    val path: Path,
    val content: String
) : Command {
    override fun run(environment: Environment) {
        environment.files.createDirectories(path.parent)
        environment.files.writeString(path, content)
    }
}
