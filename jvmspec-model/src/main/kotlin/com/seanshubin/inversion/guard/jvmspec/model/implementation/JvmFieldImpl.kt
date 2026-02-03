package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.descriptor.Signature
import com.seanshubin.inversion.guard.jvmspec.classfile.specification.AccessFlag
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.FieldInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmAttribute
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmAttributeFactory
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmField

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmFieldImpl(
    private val jvmClass: JvmClass,
    private val fieldInfo: FieldInfo,
    private val attributeFactory: JvmAttributeFactory
) : JvmField {
    override fun accessFlags(): Set<AccessFlag> {
        return fieldInfo.accessFlags
    }

    override fun className(): String {
        return jvmClass.thisClassName
    }

    override fun name(): String {
        val fieldNameIndex = fieldInfo.nameIndex
        val fieldName = jvmClass.lookupUtf8(fieldNameIndex)
        return fieldName
    }

    override fun signature(): Signature {
        val fieldDescriptorIndex = fieldInfo.descriptorIndex
        val descriptor = jvmClass.lookupDescriptor(fieldDescriptorIndex)
        return Signature(className(), name(), descriptor)
    }

    override fun attributes(): List<JvmAttribute> {
        val toJvmAttribute = { attributeInfo: AttributeInfo ->
            attributeFactory.createAttribute(jvmClass, attributeInfo)
        }
        return fieldInfo.attributes.map(toJvmAttribute)
    }
}
