package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeStackMapTableInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.StackMapFrame
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmStackMapTableAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmStackMapTableAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeStackMapTableInfo: AttributeStackMapTableInfo
) : JvmStackMapTableAttribute {
    override val numberOfEntries: UShort = attributeStackMapTableInfo.numberOfEntries
    override val entries: List<StackMapFrame> = attributeStackMapTableInfo.entries

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeStackMapTableInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeStackMapTableInfo.info
    }
}
