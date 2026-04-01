package com.seanshubin.inversion.guard.jvmspec.runtime.application

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ClassFile
import com.seanshubin.inversion.guard.di.contract.FilesContract
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClassFactory
import com.seanshubin.inversion.guard.jvmspec.output.formatting.JvmSpecFormat
import java.io.DataInputStream
import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class Runner(
    private val files: FilesContract,
    private val emit: (Any?) -> Unit,
    private val classFilePath: Path,
    private val classFactory: JvmClassFactory,
    private val format: JvmSpecFormat
) : Runnable {
    override fun run() {
        val bytes = files.readAllBytes(classFilePath)
        val input = DataInputStream(bytes.inputStream())
        val classFile = ClassFile.fromDataInput(classFilePath, input)
        val jvmClass = classFactory.createClass(classFile)
        val trees = format.classTreeList(jvmClass)
        val indent = { line: String -> "  $line" }
        trees.forEach { tree ->
            val lines = tree.toLines(indent)
            lines.forEach(emit)
        }
    }
}
