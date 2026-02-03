package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeLineNumberTableInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.LineNumberTableEntry
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmLineNumberTableAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmLineNumberTableAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeLineNumberTableInfo: AttributeLineNumberTableInfo
) : JvmLineNumberTableAttribute {
    override val lineNumberTableLength: UShort = attributeLineNumberTableInfo.lineNumberTableLength
    override val lineNumberTable: List<LineNumberTableEntry> = attributeLineNumberTableInfo.lineNumberTable

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeLineNumberTableInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeLineNumberTableInfo.info
    }
}
