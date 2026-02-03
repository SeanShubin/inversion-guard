package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeSourceDebugExtensionInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmSourceDebugExtensionAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmSourceDebugExtensionAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeSourceDebugExtensionInfo: AttributeSourceDebugExtensionInfo
) : JvmSourceDebugExtensionAttribute {
    override fun name(): String {
        return jvmClass.lookupUtf8(attributeSourceDebugExtensionInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeSourceDebugExtensionInfo.info
    }

    override fun debugExtension(): String {
        return String(attributeSourceDebugExtensionInfo.info.toByteArray(), Charsets.UTF_8)
    }
}
