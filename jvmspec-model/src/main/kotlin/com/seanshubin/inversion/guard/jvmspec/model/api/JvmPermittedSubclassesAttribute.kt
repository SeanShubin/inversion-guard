package com.seanshubin.inversion.guard.jvmspec.model.api

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmPermittedSubclassesAttribute : JvmAttribute {
    val numberOfClasses: UShort
    val classes: List<UShort>
    fun classNames(): List<String>
}
