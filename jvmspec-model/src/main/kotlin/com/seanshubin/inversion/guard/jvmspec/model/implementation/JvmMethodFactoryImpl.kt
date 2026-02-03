package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.MethodInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmAttributeFactory
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmMethod
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmMethodFactory

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmMethodFactoryImpl(
    private val attributeFactory: JvmAttributeFactory
) : JvmMethodFactory {
    override fun createMethod(jvmClass: JvmClass, methodInfo: MethodInfo): JvmMethod =
        JvmMethodImpl(jvmClass, methodInfo, attributeFactory)
}
