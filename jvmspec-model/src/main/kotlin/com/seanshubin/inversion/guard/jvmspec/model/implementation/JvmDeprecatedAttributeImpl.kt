package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeDeprecatedInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmDeprecatedAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmDeprecatedAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeDeprecatedInfo: AttributeDeprecatedInfo
) : JvmDeprecatedAttribute {
    override fun name(): String {
        return jvmClass.lookupUtf8(attributeDeprecatedInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeDeprecatedInfo.info
    }
}
