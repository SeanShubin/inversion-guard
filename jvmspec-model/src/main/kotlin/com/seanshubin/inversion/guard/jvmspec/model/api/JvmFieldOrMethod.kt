package com.seanshubin.inversion.guard.jvmspec.model.api

import com.seanshubin.inversion.guard.jvmspec.classfile.descriptor.Signature
import com.seanshubin.inversion.guard.jvmspec.classfile.specification.AccessFlag

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmFieldOrMethod {
    fun className(): String
    fun name(): String
    fun signature(): Signature
    fun attributes(): List<JvmAttribute>
    fun accessFlags(): Set<AccessFlag>
    fun ref(): JvmRef {
        return JvmRef(className(), name(), signature())
    }
}
