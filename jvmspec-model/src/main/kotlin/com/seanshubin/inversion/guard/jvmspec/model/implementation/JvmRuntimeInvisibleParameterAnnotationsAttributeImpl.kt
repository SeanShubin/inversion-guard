package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeRuntimeInvisibleParameterAnnotationsInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ParameterAnnotation
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmRuntimeInvisibleParameterAnnotationsAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmRuntimeInvisibleParameterAnnotationsAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeRuntimeInvisibleParameterAnnotationsInfo: AttributeRuntimeInvisibleParameterAnnotationsInfo
) : JvmRuntimeInvisibleParameterAnnotationsAttribute {
    override val numParameters: UByte = attributeRuntimeInvisibleParameterAnnotationsInfo.numParameters
    override val parameterAnnotations: List<ParameterAnnotation> =
        attributeRuntimeInvisibleParameterAnnotationsInfo.parameterAnnotations

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeRuntimeInvisibleParameterAnnotationsInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeRuntimeInvisibleParameterAnnotationsInfo.info
    }
}
