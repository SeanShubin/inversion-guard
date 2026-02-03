package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AnnotationStructure.Annotation
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeRuntimeInvisibleAnnotationsInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmRuntimeInvisibleAnnotationsAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmRuntimeInvisibleAnnotationsAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeRuntimeInvisibleAnnotationsInfo: AttributeRuntimeInvisibleAnnotationsInfo
) : JvmRuntimeInvisibleAnnotationsAttribute {
    override val numAnnotations: UShort = attributeRuntimeInvisibleAnnotationsInfo.numAnnotations
    override val annotations: List<Annotation> = attributeRuntimeInvisibleAnnotationsInfo.annotations

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeRuntimeInvisibleAnnotationsInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeRuntimeInvisibleAnnotationsInfo.info
    }
}
