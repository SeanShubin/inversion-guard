package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeRuntimeInvisibleTypeAnnotationsInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.TypeAnnotation
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmRuntimeInvisibleTypeAnnotationsAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmRuntimeInvisibleTypeAnnotationsAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeRuntimeInvisibleTypeAnnotationsInfo: AttributeRuntimeInvisibleTypeAnnotationsInfo
) : JvmRuntimeInvisibleTypeAnnotationsAttribute {
    override val numAnnotations: UShort = attributeRuntimeInvisibleTypeAnnotationsInfo.numAnnotations
    override val annotations: List<TypeAnnotation> = attributeRuntimeInvisibleTypeAnnotationsInfo.annotations

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeRuntimeInvisibleTypeAnnotationsInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeRuntimeInvisibleTypeAnnotationsInfo.info
    }
}
