package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeRuntimeVisibleParameterAnnotationsInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ParameterAnnotation
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmRuntimeVisibleParameterAnnotationsAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmRuntimeVisibleParameterAnnotationsAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeRuntimeVisibleParameterAnnotationsInfo: AttributeRuntimeVisibleParameterAnnotationsInfo
) : JvmRuntimeVisibleParameterAnnotationsAttribute {
    override val numParameters: UByte = attributeRuntimeVisibleParameterAnnotationsInfo.numParameters
    override val parameterAnnotations: List<ParameterAnnotation> =
        attributeRuntimeVisibleParameterAnnotationsInfo.parameterAnnotations

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeRuntimeVisibleParameterAnnotationsInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeRuntimeVisibleParameterAnnotationsInfo.info
    }
}
