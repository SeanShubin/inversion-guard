package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class ModuleOpens(
    val opensIndex: UShort,
    val opensFlags: Int,
    val opensToCount: UShort,
    val opensToIndex: List<UShort>
) {
    companion object {
        fun fromDataInput(input: DataInput): ModuleOpens {
            val opensIndex = input.readUnsignedShort().toUShort()
            val opensFlags = input.readUnsignedShort()
            val opensToCount = input.readUnsignedShort().toUShort()
            val opensToIndex = (0 until opensToCount.toInt()).map {
                input.readUnsignedShort().toUShort()
            }
            return ModuleOpens(opensIndex, opensFlags, opensToCount, opensToIndex)
        }
    }
}
