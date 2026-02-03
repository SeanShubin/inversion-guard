package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributePermittedSubclassesInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmPermittedSubclassesAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmPermittedSubclassesAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributePermittedSubclassesInfo: AttributePermittedSubclassesInfo
) : JvmPermittedSubclassesAttribute {
    override val numberOfClasses: UShort = attributePermittedSubclassesInfo.numberOfClasses
    override val classes: List<UShort> = attributePermittedSubclassesInfo.classes

    override fun name(): String {
        return jvmClass.lookupUtf8(attributePermittedSubclassesInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributePermittedSubclassesInfo.info
    }

    override fun classNames(): List<String> {
        return classes.map { classIndex ->
            jvmClass.lookupClassName(classIndex)
        }
    }
}
