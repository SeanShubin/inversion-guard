package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeModulePackagesInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmModulePackagesAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmModulePackagesAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeModulePackagesInfo: AttributeModulePackagesInfo
) : JvmModulePackagesAttribute {
    override val packageCount: UShort = attributeModulePackagesInfo.packageCount
    override val packageIndex: List<UShort> = attributeModulePackagesInfo.packageIndex

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeModulePackagesInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeModulePackagesInfo.info
    }
}
