package com.seanshubin.inversion.guard.jvmspec.model.api

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class JvmExceptionTable(
    val startProgramCounter: UShort,
    val endProgramCounter: UShort,
    val handlerProgramCounter: UShort,
    val catchType: UShort
)
