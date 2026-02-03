package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class ModuleHash(
    val moduleNameIndex: UShort,
    val hashLength: UShort,
    val hash: List<Byte>
) {
    companion object {
        fun fromDataInput(input: DataInput): ModuleHash {
            val moduleNameIndex = input.readUnsignedShort().toUShort()
            val hashLength = input.readUnsignedShort().toUShort()
            val hash = (0 until hashLength.toInt()).map {
                input.readByte()
            }
            return ModuleHash(moduleNameIndex, hashLength, hash)
        }
    }
}
