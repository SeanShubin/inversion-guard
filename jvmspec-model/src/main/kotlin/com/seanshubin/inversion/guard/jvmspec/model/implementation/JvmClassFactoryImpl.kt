package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ClassFile
import com.seanshubin.inversion.guard.jvmspec.model.api.*

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmClassFactoryImpl(
    private val methodFactory: JvmMethodFactory,
    private val fieldFactory: JvmFieldFactory,
    private val attributeFactory: JvmAttributeFactory
) : JvmClassFactory {
    override fun createClass(classFile: ClassFile): JvmClass =
        JvmClassImpl(classFile, methodFactory, fieldFactory, attributeFactory)
}
