package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class InnerClassInfo(
    val innerClassInfoIndex: UShort,
    val outerClassInfoIndex: UShort,
    val innerNameIndex: UShort,
    val innerClassAccessFlags: UShort
) {
    companion object {
        fun fromDataInput(input: DataInput): InnerClassInfo {
            val innerClassInfoIndex = input.readUnsignedShort().toUShort()
            val outerClassInfoIndex = input.readUnsignedShort().toUShort()
            val innerNameIndex = input.readUnsignedShort().toUShort()
            val innerClassAccessFlags = input.readUnsignedShort().toUShort()
            return InnerClassInfo(innerClassInfoIndex, outerClassInfoIndex, innerNameIndex, innerClassAccessFlags)
        }
    }
}
