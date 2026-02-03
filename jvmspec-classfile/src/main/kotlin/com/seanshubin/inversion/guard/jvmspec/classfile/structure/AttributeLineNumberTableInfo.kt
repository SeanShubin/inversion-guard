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

data class AttributeLineNumberTableInfo(
    override val attributeIndex: UShort,
    override val attributeLength: Int,
    override val info: List<Byte>,
    val lineNumberTableLength: UShort,
    val lineNumberTable: List<LineNumberTableEntry>
) : AttributeInfo {
    companion object {
        const val NAME = "LineNumberTable"
        fun fromAttributeInfo(
            attributeInfo: AttributeInfo,
            constantPoolMap: Map<UShort, ConstantInfo>,
            attributeInfoFromDataInput: (DataInput, Map<UShort, ConstantInfo>) -> AttributeInfo
        ): AttributeLineNumberTableInfo {
            val input = DataInputStream(ByteArrayInputStream(attributeInfo.info.toByteArray()))
            val lineNumberTableLength = input.readUnsignedShort().toUShort()
            val lineNumberTable = (0 until lineNumberTableLength.toInt()).map {
                LineNumberTableEntry.fromDataInput(input)
            }
            return AttributeLineNumberTableInfo(
                attributeInfo.attributeIndex,
                attributeInfo.attributeLength,
                attributeInfo.info,
                lineNumberTableLength,
                lineNumberTable
            )
        }
    }
}
