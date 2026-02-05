package com.seanshubin.inversion.guard.command

import com.seanshubin.inversion.guard.appconfig.Environment
import com.seanshubin.inversion.guard.jvmspec.infrastructure.serialization.JsonMappers.toJson
import java.nio.file.Path

data class CreateJsonFileCommand(
    val path: Path,
    val data: Any
) : Command {
    override fun run(environment: Environment) {
        environment.files.createDirectories(path.parent)
        val jsonContent = data.toJson()
        environment.files.writeString(path, jsonContent)
    }
}
