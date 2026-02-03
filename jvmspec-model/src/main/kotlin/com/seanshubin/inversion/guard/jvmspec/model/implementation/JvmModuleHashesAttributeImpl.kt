package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeModuleHashesInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ModuleHash
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmModuleHashesAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmModuleHashesAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeModuleHashesInfo: AttributeModuleHashesInfo
) : JvmModuleHashesAttribute {
    override val algorithmIndex: UShort = attributeModuleHashesInfo.algorithmIndex
    override val modulesCount: UShort = attributeModuleHashesInfo.modulesCount
    override val modules: List<ModuleHash> = attributeModuleHashesInfo.modules

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeModuleHashesInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeModuleHashesInfo.info
    }

    override fun algorithm(): String {
        return jvmClass.lookupUtf8(algorithmIndex)
    }
}
