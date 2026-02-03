package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeSignatureInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmSignatureAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmSignatureAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeSignatureInfo: AttributeSignatureInfo
) : JvmSignatureAttribute {
    override val signatureIndex: UShort = attributeSignatureInfo.signatureIndex

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeSignatureInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeSignatureInfo.info
    }

    override fun signature(): String {
        return jvmClass.lookupUtf8(signatureIndex)
    }
}
