package com.seanshubin.inversion.guard.jvmspec.model.api

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.LocalVariableTypeTableEntry

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmLocalVariableTypeTableAttribute : JvmAttribute {
    val localVariableTypeTableLength: UShort
    val localVariableTypeTable: List<LocalVariableTypeTableEntry>
}
