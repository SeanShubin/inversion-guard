package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeRuntimeVisibleTypeAnnotationsInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.TypeAnnotation
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmRuntimeVisibleTypeAnnotationsAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmRuntimeVisibleTypeAnnotationsAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeRuntimeVisibleTypeAnnotationsInfo: AttributeRuntimeVisibleTypeAnnotationsInfo
) : JvmRuntimeVisibleTypeAnnotationsAttribute {
    override val numAnnotations: UShort = attributeRuntimeVisibleTypeAnnotationsInfo.numAnnotations
    override val annotations: List<TypeAnnotation> = attributeRuntimeVisibleTypeAnnotationsInfo.annotations

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeRuntimeVisibleTypeAnnotationsInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeRuntimeVisibleTypeAnnotationsInfo.info
    }
}
