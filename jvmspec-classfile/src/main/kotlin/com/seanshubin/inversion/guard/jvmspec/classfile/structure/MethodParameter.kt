package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class MethodParameter(
    val nameIndex: UShort,
    val accessFlags: UShort
) {
    companion object {
        fun fromDataInput(input: DataInput): MethodParameter {
            val nameIndex = input.readUnsignedShort().toUShort()
            val accessFlags = input.readUnsignedShort().toUShort()
            return MethodParameter(nameIndex, accessFlags)
        }
    }
}
