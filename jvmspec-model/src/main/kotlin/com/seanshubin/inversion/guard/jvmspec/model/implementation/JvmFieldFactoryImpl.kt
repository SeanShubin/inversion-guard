package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.FieldInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmAttributeFactory
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmField
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmFieldFactory

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmFieldFactoryImpl(
    private val attributeFactory: JvmAttributeFactory
) : JvmFieldFactory {
    override fun createField(jvmClass: JvmClass, fieldInfo: FieldInfo): JvmField =
        JvmFieldImpl(jvmClass, fieldInfo, attributeFactory)
}
