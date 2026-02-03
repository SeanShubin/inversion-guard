package com.seanshubin.inversion.guard.jvmspec.model.api

import com.seanshubin.inversion.guard.jvmspec.classfile.specification.ArrayType

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

sealed interface JvmArgument {
    data class Constant(val value: JvmConstant) : JvmArgument
    data class IntValue(val value: Int) : JvmArgument
    data class ArrayTypeValue(val value: ArrayType) : JvmArgument
    data class LookupSwitch(val default: Int, val lookup: List<Pair<Int, Int>>) : JvmArgument
    data class TableSwitch(val default: Int, val low: Int, val high: Int, val jumpOffsets: List<Int>) : JvmArgument
    data class OpCodeValue(val name: String, val code: UByte) : JvmArgument
}
