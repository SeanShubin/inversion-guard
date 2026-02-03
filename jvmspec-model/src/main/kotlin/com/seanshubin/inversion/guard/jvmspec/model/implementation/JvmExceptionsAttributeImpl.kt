package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeExceptionsInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmExceptionsAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmExceptionsAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeExceptionsInfo: AttributeExceptionsInfo
) : JvmExceptionsAttribute {
    override val numberOfExceptions: UShort = attributeExceptionsInfo.numberOfExceptions
    override val exceptionIndexTable: List<UShort> = attributeExceptionsInfo.exceptionIndexTable

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeExceptionsInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeExceptionsInfo.info
    }

    override fun exceptionClassNames(): List<String> {
        return exceptionIndexTable.map { index ->
            jvmClass.lookupClassName(index)
        }
    }
}
