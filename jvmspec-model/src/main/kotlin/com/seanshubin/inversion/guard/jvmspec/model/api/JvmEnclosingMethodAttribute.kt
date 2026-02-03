package com.seanshubin.inversion.guard.jvmspec.model.api

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmEnclosingMethodAttribute : JvmAttribute {
    val classIndex: UShort
    val methodIndex: UShort
    fun className(): String
}
