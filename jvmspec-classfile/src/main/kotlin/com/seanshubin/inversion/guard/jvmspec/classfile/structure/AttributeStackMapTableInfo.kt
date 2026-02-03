package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.ByteArrayInputStream
import java.io.DataInput
import java.io.DataInputStream

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class AttributeStackMapTableInfo(
    override val attributeIndex: UShort,
    override val attributeLength: Int,
    override val info: List<Byte>,
    val numberOfEntries: UShort,
    val entries: List<StackMapFrame>
) : AttributeInfo {
    companion object {
        const val NAME = "StackMapTable"
        fun fromAttributeInfo(
            attributeInfo: AttributeInfo,
            constantPoolMap: Map<UShort, ConstantInfo>,
            attributeInfoFromDataInput: (DataInput, Map<UShort, ConstantInfo>) -> AttributeInfo
        ): AttributeStackMapTableInfo {
            val input = DataInputStream(ByteArrayInputStream(attributeInfo.info.toByteArray()))
            val numberOfEntries = input.readUnsignedShort().toUShort()
            val entries = (0 until numberOfEntries.toInt()).map {
                StackMapFrame.fromDataInput(input)
            }
            return AttributeStackMapTableInfo(
                attributeInfo.attributeIndex,
                attributeInfo.attributeLength,
                attributeInfo.info,
                numberOfEntries,
                entries
            )
        }
    }
}
