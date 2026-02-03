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

data class ConstantFloatInfo(
    override val tag: ConstantPoolTag,
    override val index: UShort,
    val floatValue: Float
) : ConstantInfo {
    override val entriesTaken: Int get() = 1

    companion object {
        val TAG: ConstantPoolTag = ConstantPoolTag.FLOAT

        fun fromDataInput(tag: ConstantPoolTag, index: UShort, input: DataInput): ConstantFloatInfo {
            val floatValue = input.readFloat()
            return ConstantFloatInfo(tag, index, floatValue)
        }
    }
}
