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

data class AttributeModuleMainClassInfo(
    override val attributeIndex: UShort,
    override val attributeLength: Int,
    override val info: List<Byte>,
    val mainClassIndex: UShort
) : AttributeInfo {
    companion object {
        const val NAME = "ModuleMainClass"
        fun fromAttributeInfo(
            attributeInfo: AttributeInfo,
            constantPoolMap: Map<UShort, ConstantInfo>,
            attributeInfoFromDataInput: (DataInput, Map<UShort, ConstantInfo>) -> AttributeInfo
        ): AttributeModuleMainClassInfo {
            val input = DataInputStream(ByteArrayInputStream(attributeInfo.info.toByteArray()))
            val mainClassIndex = input.readUnsignedShort().toUShort()
            return AttributeModuleMainClassInfo(
                attributeInfo.attributeIndex,
                attributeInfo.attributeLength,
                attributeInfo.info,
                mainClassIndex
            )
        }
    }
}
