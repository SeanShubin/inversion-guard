package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.command.Command
import com.seanshubin.inversion.guard.command.CreateFileCommand
import com.seanshubin.inversion.guard.jvmspec.infrastructure.filesystem.PathUtil.removeExtension
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.output.formatting.JvmSpecFormat
import com.seanshubin.inversion.guard.reporting.ReportCategory
import java.nio.file.Path

class ClassProcessorImpl(
    private val baseDir: Path,
    private val outputDir: Path,
    private val format: JvmSpecFormat
) : ClassProcessor {
    override fun processClass(classAnalysis: ClassAnalysis): List<Command> {
        val relativePath = baseDir.relativize(classAnalysis.jvmClass.origin)
        val baseFileName =
            outputDir.resolve(ReportCategory.BROWSE.directory).resolve(relativePath).removeExtension("class")
        val disassemblyCommands = createDisassemblyCommands(baseFileName, classAnalysis.jvmClass)
        return disassemblyCommands
    }

    private fun createDisassemblyCommands(baseFileName: String, jvmClass: JvmClass): List<Command> {
        val treeList = format.classTreeList(jvmClass)
        val filePath = Path.of("$baseFileName-disassembled.txt")
        val disassemblyCommand = CreateFileCommand(filePath, treeList)
        return listOf(disassemblyCommand)
    }
}