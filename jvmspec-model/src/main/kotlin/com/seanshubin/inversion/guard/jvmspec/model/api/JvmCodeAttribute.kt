package com.seanshubin.inversion.guard.jvmspec.model.api

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmCodeAttribute : JvmAttribute {
    fun complexity(): Int
    fun instructions(): List<JvmInstruction>
    fun exceptionTable(): List<JvmExceptionTable>
    fun attributes(): List<JvmAttribute>
    val maxStack: UShort
    val maxLocals: UShort
    val codeLength: Int
}
