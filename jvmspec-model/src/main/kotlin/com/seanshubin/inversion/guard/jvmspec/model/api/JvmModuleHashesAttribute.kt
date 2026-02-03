package com.seanshubin.inversion.guard.jvmspec.model.api

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ModuleHash

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmModuleHashesAttribute : JvmAttribute {
    val algorithmIndex: UShort
    val modulesCount: UShort
    val modules: List<ModuleHash>
    fun algorithm(): String
}
