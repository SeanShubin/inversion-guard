package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import com.seanshubin.inversion.guard.jvmspec.classfile.specification.ConstantPoolTag
import com.seanshubin.inversion.guard.jvmspec.classfile.specification.ReferenceKind
import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class ConstantMethodHandleInfo(
    override val tag: ConstantPoolTag,
    override val index: UShort,
    val referenceKind: ReferenceKind,
    val referenceIndex: UShort
) : ConstantInfo {
    override val entriesTaken: Int get() = 1

    companion object {
        val TAG: ConstantPoolTag = ConstantPoolTag.METHOD_HANDLE

        fun fromDataInput(tag: ConstantPoolTag, index: UShort, input: DataInput): ConstantMethodHandleInfo {
            val referenceKindUByte = input.readUnsignedByte().toUByte()
            val referenceKind = ReferenceKind.fromCode(referenceKindUByte)
            val referenceIndex = input.readUnsignedShort().toUShort()
            return ConstantMethodHandleInfo(tag, index, referenceKind, referenceIndex)
        }
    }
}
