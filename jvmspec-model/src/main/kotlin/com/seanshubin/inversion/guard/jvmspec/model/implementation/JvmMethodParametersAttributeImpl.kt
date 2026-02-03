package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeMethodParametersInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.MethodParameter
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmMethodParametersAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmMethodParametersAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeMethodParametersInfo: AttributeMethodParametersInfo
) : JvmMethodParametersAttribute {
    override val parametersCount: UByte = attributeMethodParametersInfo.parametersCount
    override val parameters: List<MethodParameter> = attributeMethodParametersInfo.parameters

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeMethodParametersInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeMethodParametersInfo.info
    }
}
