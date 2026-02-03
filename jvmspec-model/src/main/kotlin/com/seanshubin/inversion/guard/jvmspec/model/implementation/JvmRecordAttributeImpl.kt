package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeRecordInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.RecordComponentInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmRecordAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmRecordAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeRecordInfo: AttributeRecordInfo
) : JvmRecordAttribute {
    override val componentsCount: UShort = attributeRecordInfo.componentsCount
    override val components: List<RecordComponentInfo> = attributeRecordInfo.components

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeRecordInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeRecordInfo.info
    }
}
