package com.seanshubin.inversion.guard.jvmspec.model.api

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmInstruction {
    fun name(): String
    fun code(): UByte
    fun bytes(): List<Byte>
    fun args(): List<JvmArgument>
}
