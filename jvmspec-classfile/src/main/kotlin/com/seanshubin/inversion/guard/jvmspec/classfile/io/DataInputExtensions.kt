package com.seanshubin.inversion.guard.jvmspec.classfile.io

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

object DataInputExtensions {
    fun DataInput.readByteList(size: Int): List<Byte> {
        val byteArray = ByteArray(size)
        this.readFully(byteArray)
        return byteArray.toList()
    }
}
