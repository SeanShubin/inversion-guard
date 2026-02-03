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

data class ConstantIntegerInfo(
    override val tag: ConstantPoolTag,
    override val index: UShort,
    val intValue: Int
) : ConstantInfo {
    override val entriesTaken: Int get() = 1

    companion object {
        val TAG: ConstantPoolTag = ConstantPoolTag.INTEGER

        fun fromDataInput(tag: ConstantPoolTag, index: UShort, input: DataInput): ConstantIntegerInfo {
            val intValue = input.readInt()
            return ConstantIntegerInfo(tag, index, intValue)
        }
    }
}
