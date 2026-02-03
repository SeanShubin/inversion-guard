package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeEnclosingMethodInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmEnclosingMethodAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmEnclosingMethodAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeEnclosingMethodInfo: AttributeEnclosingMethodInfo
) : JvmEnclosingMethodAttribute {
    override val classIndex: UShort = attributeEnclosingMethodInfo.classIndex
    override val methodIndex: UShort = attributeEnclosingMethodInfo.methodIndex

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeEnclosingMethodInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeEnclosingMethodInfo.info
    }

    override fun className(): String {
        return jvmClass.lookupClassName(classIndex)
    }
}
