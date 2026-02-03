package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import com.seanshubin.inversion.guard.jvmspec.classfile.specification.ConstantPoolTag
import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class ConstantStringInfo(
    override val tag: ConstantPoolTag,
    override val index: UShort,
    val stringIndex: UShort
) : ConstantInfo {
    override val entriesTaken: Int get() = 1

    companion object {
        val TAG: ConstantPoolTag = ConstantPoolTag.STRING

        fun fromDataInput(tag: ConstantPoolTag, index: UShort, input: DataInput): ConstantStringInfo {
            val stringIndex = input.readUnsignedShort().toUShort()
            return ConstantStringInfo(tag, index, stringIndex)
        }
    }
}
