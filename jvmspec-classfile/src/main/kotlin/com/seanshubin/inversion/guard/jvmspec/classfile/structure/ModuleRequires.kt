package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class ModuleRequires(
    val requiresIndex: UShort,
    val requiresFlags: Int,
    val requiresVersionIndex: UShort
) {
    companion object {
        fun fromDataInput(input: DataInput): ModuleRequires {
            val requiresIndex = input.readUnsignedShort().toUShort()
            val requiresFlags = input.readUnsignedShort()
            val requiresVersionIndex = input.readUnsignedShort().toUShort()
            return ModuleRequires(requiresIndex, requiresFlags, requiresVersionIndex)
        }
    }
}
