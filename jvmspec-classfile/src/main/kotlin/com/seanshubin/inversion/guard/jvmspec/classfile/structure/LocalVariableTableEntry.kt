package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class LocalVariableTableEntry(
    val startPc: UShort,
    val length: UShort,
    val nameIndex: UShort,
    val descriptorIndex: UShort,
    val index: UShort
) {
    companion object {
        fun fromDataInput(input: DataInput): LocalVariableTableEntry {
            val startPc = input.readUnsignedShort().toUShort()
            val length = input.readUnsignedShort().toUShort()
            val nameIndex = input.readUnsignedShort().toUShort()
            val descriptorIndex = input.readUnsignedShort().toUShort()
            val index = input.readUnsignedShort().toUShort()
            return LocalVariableTableEntry(startPc, length, nameIndex, descriptorIndex, index)
        }
    }
}
