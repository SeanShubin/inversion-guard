package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AnnotationStructure.Annotation
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeRuntimeVisibleAnnotationsInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmRuntimeVisibleAnnotationsAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmRuntimeVisibleAnnotationsAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeRuntimeVisibleAnnotationsInfo: AttributeRuntimeVisibleAnnotationsInfo
) : JvmRuntimeVisibleAnnotationsAttribute {
    override val numAnnotations: UShort = attributeRuntimeVisibleAnnotationsInfo.numAnnotations
    override val annotations: List<Annotation> = attributeRuntimeVisibleAnnotationsInfo.annotations

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeRuntimeVisibleAnnotationsInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeRuntimeVisibleAnnotationsInfo.info
    }
}
