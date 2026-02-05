package com.seanshubin.inversion.guard.jvmspec.model.conversion

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ClassFile
import com.seanshubin.inversion.guard.di.contract.FilesContract
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClassFactory
import java.io.DataInput
import java.io.DataInputStream
import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class Converter(private val jvmClassFactory: JvmClassFactory) {
    fun Path.toDataInput(files: FilesContract): DataInput {
        val inputStream = files.newInputStream(this)
        return DataInputStream(inputStream)
    }

    fun DataInput.toClassFile(origin: Path): ClassFile = ClassFile.fromDataInput(origin, this)

    fun ClassFile.toJvmClass(): JvmClass = jvmClassFactory.createClass(this)

    fun Path.toJvmClass(files: FilesContract, origin: Path): JvmClass =
        toDataInput(files).toClassFile(origin).toJvmClass()
}
