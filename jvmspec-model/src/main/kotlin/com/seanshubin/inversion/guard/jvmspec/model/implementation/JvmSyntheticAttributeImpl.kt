package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeSyntheticInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmSyntheticAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmSyntheticAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeSyntheticInfo: AttributeSyntheticInfo
) : JvmSyntheticAttribute {
    override fun name(): String {
        return jvmClass.lookupUtf8(attributeSyntheticInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeSyntheticInfo.info
    }
}
