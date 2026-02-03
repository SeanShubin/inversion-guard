package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeConstantValueInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmConstant
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmConstantValueAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmConstantValueAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeConstantValueInfo: AttributeConstantValueInfo
) : JvmConstantValueAttribute {
    override val constantValueIndex: UShort = attributeConstantValueInfo.constantValueIndex

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeConstantValueInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeConstantValueInfo.info
    }

    override fun constantValue(): String {
        val constant = jvmClass.constants.getValue(constantValueIndex)
        return when (constant) {
            is JvmConstant.JvmConstantInteger -> constant.value.toString()
            is JvmConstant.JvmConstantFloat -> constant.value.toString()
            is JvmConstant.JvmConstantLong -> constant.value.toString()
            is JvmConstant.JvmConstantDouble -> constant.value.toString()
            is JvmConstant.JvmConstantString -> constant.value
            else -> constant.toString()
        }
    }
}
