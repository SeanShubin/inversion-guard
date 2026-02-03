package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AnnotationStructure.ElementValue
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeAnnotationDefaultInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmAnnotationDefaultAttribute
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmAnnotationDefaultAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeAnnotationDefaultInfo: AttributeAnnotationDefaultInfo
) : JvmAnnotationDefaultAttribute {
    override val defaultValue: ElementValue = attributeAnnotationDefaultInfo.defaultValue

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeAnnotationDefaultInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeAnnotationDefaultInfo.info
    }
}
