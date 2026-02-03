package com.seanshubin.inversion.guard.command

import com.seanshubin.inversion.guard.jvmspec.infrastructure.collections.Tree
import java.nio.file.Path

data class CreateFileCommand(val path: Path, val roots: List<Tree>) : Command {
    override fun run(environment: Environment) {
        environment.files.createDirectories(path.parent)
        val lines = roots.flatMap { tree -> tree.toLines { line -> "  $line" } }
        environment.files.write(path, lines)
    }
}
