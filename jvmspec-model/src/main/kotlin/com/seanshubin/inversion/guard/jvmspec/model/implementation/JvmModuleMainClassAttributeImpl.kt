package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeModuleMainClassInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmModuleMainClassAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmModuleMainClassAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeModuleMainClassInfo: AttributeModuleMainClassInfo
) : JvmModuleMainClassAttribute {
    override val mainClassIndex: UShort = attributeModuleMainClassInfo.mainClassIndex

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeModuleMainClassInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeModuleMainClassInfo.info
    }

    override fun mainClassName(): String {
        return jvmClass.lookupClassName(mainClassIndex)
    }
}
