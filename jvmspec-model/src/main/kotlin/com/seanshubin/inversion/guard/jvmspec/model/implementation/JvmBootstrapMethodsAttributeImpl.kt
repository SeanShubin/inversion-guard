package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeBootstrapMethodsInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.BootstrapMethod
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmBootstrapMethodsAttribute
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmBootstrapMethodsAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeBootstrapMethodsInfo: AttributeBootstrapMethodsInfo
) : JvmBootstrapMethodsAttribute {
    override val numBootstrapMethods: UShort = attributeBootstrapMethodsInfo.numBootstrapMethods
    override val bootstrapMethods: List<BootstrapMethod> = attributeBootstrapMethodsInfo.bootstrapMethods

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeBootstrapMethodsInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeBootstrapMethodsInfo.info
    }
}
