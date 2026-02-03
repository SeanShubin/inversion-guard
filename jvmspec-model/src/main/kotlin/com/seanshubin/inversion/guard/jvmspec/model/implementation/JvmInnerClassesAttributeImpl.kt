package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeInnerClassesInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.InnerClassInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmInnerClassesAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmInnerClassesAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeInnerClassesInfo: AttributeInnerClassesInfo
) : JvmInnerClassesAttribute {
    override val numberOfClasses: UShort = attributeInnerClassesInfo.numberOfClasses
    override val classes: List<InnerClassInfo> = attributeInnerClassesInfo.classes

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeInnerClassesInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeInnerClassesInfo.info
    }
}
