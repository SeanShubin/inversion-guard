package com.seanshubin.inversion.guard.jvmspec.model.api

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.MethodParameter

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmMethodParametersAttribute : JvmAttribute {
    val parametersCount: UByte
    val parameters: List<MethodParameter>
}
