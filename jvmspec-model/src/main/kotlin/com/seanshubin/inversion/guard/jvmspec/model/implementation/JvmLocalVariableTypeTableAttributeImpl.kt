package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeLocalVariableTypeTableInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.LocalVariableTypeTableEntry
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmLocalVariableTypeTableAttribute

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmLocalVariableTypeTableAttributeImpl(
    private val jvmClass: JvmClass,
    private val attributeLocalVariableTypeTableInfo: AttributeLocalVariableTypeTableInfo
) : JvmLocalVariableTypeTableAttribute {
    override val localVariableTypeTableLength: UShort = attributeLocalVariableTypeTableInfo.localVariableTypeTableLength
    override val localVariableTypeTable: List<LocalVariableTypeTableEntry> =
        attributeLocalVariableTypeTableInfo.localVariableTypeTable

    override fun name(): String {
        return jvmClass.lookupUtf8(attributeLocalVariableTypeTableInfo.attributeIndex)
    }

    override fun bytes(): List<Byte> {
        return attributeLocalVariableTypeTableInfo.info
    }
}
