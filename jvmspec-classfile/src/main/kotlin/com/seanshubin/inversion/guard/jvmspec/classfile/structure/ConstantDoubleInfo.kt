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

data class ConstantDoubleInfo(
    override val tag: ConstantPoolTag,
    override val index: UShort,
    val doubleValue: Double
) : ConstantInfo {
    override val entriesTaken: Int get() = 2

    companion object {
        val TAG: ConstantPoolTag = ConstantPoolTag.DOUBLE
        fun fromDataInput(tag: ConstantPoolTag, index: UShort, input: DataInput): ConstantDoubleInfo {
            val value = input.readDouble()
            return ConstantDoubleInfo(tag, index, value)
        }
    }
}
