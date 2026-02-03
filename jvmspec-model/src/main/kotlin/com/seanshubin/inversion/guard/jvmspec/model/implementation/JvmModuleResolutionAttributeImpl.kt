package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeModuleResolutionInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmModuleResolutionAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmModuleResolutionAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeModuleResolutionInfo: AttributeModuleResolutionInfo
) : JvmModuleResolutionAttribute {
    override val resolutionFlags: Int = attributeModuleResolutionInfo.resolutionFlags

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeModuleResolutionInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeModuleResolutionInfo.info
    }
}
