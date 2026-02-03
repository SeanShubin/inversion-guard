package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmAttribute
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeInfo: AttributeInfo
) : JvmAttribute {
    override fun name(): String {
        val nameIndex = attributeInfo.attributeIndex
        return jvmClass.lookupUtf8(nameIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeInfo.info
    }
}
