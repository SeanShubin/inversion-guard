package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class ModuleExports(
    val exportsIndex: UShort,
    val exportsFlags: Int,
    val exportsToCount: UShort,
    val exportsToIndex: List<UShort>
) {
    companion object {
        fun fromDataInput(input: DataInput): ModuleExports {
            val exportsIndex = input.readUnsignedShort().toUShort()
            val exportsFlags = input.readUnsignedShort()
            val exportsToCount = input.readUnsignedShort().toUShort()
            val exportsToIndex = (0 until exportsToCount.toInt()).map {
                input.readUnsignedShort().toUShort()
            }
            return ModuleExports(exportsIndex, exportsFlags, exportsToCount, exportsToIndex)
        }
    }
}
