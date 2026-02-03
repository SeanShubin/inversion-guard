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

data class ConstantLongInfo(
    override val tag: ConstantPoolTag,
    override val index: UShort,
    val longValue: Long
) : ConstantInfo {
    override val entriesTaken: Int get() = 2

    companion object {
        val TAG: ConstantPoolTag = ConstantPoolTag.LONG

        fun fromDataInput(tag: ConstantPoolTag, index: UShort, input: DataInput): ConstantLongInfo {
            val longValue = input.readLong()
            return ConstantLongInfo(tag, index, longValue)
        }
    }
}
