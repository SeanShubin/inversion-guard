package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeNestHostInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmNestHostAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmNestHostAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeNestHostInfo: AttributeNestHostInfo
) : JvmNestHostAttribute {
    override val hostClassIndex: UShort = attributeNestHostInfo.hostClassIndex

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeNestHostInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeNestHostInfo.info
    }

    override fun hostClassName(): String {
        return jvmClass.lookupClassName(hostClassIndex)
    }
}
