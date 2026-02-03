package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class LocalVariableTypeTableEntry(
    val startPc: UShort,
    val length: UShort,
    val nameIndex: UShort,
    val signatureIndex: UShort,
    val index: UShort
) {
    companion object {
        fun fromDataInput(input: DataInput): LocalVariableTypeTableEntry {
            val startPc = input.readUnsignedShort().toUShort()
            val length = input.readUnsignedShort().toUShort()
            val nameIndex = input.readUnsignedShort().toUShort()
            val signatureIndex = input.readUnsignedShort().toUShort()
            val index = input.readUnsignedShort().toUShort()
            return LocalVariableTypeTableEntry(startPc, length, nameIndex, signatureIndex, index)
        }
    }
}
