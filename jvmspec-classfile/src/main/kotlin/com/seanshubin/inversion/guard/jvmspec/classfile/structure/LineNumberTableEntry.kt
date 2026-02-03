package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class LineNumberTableEntry(
    val startPc: UShort,
    val lineNumber: UShort
) {
    companion object {
        fun fromDataInput(input: DataInput): LineNumberTableEntry {
            val startPc = input.readUnsignedShort().toUShort()
            val lineNumber = input.readUnsignedShort().toUShort()
            return LineNumberTableEntry(startPc, lineNumber)
        }
    }
}
