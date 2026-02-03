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

data class ConstantUtf8Info(
    override val tag: ConstantPoolTag,
    override val index: UShort,
    val length: UShort,
    val utf8Value: String
) : ConstantInfo {
    override val entriesTaken: Int get() = 1

    companion object {
        val TAG: ConstantPoolTag = ConstantPoolTag.UTF8

        fun fromDataInput(tag: ConstantPoolTag, index: UShort, input: DataInput): ConstantUtf8Info {
            val length = input.readUnsignedShort().toUShort()
            val bytes: ByteArray = ByteArray(length.toInt())
            input.readFully(bytes)
            val utf8Value = String(bytes)
            return ConstantUtf8Info(tag, index, length, utf8Value)
        }
    }
}
