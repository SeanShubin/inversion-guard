package com.seanshubin.inversion.guard.jvmspec.model.api

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ModuleExports
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ModuleOpens
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ModuleProvides
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ModuleRequires

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmModuleAttribute : JvmAttribute {
    val moduleNameIndex: UShort
    val moduleFlags: Int
    val moduleVersionIndex: UShort
    val requiresCount: UShort
    val requires: List<ModuleRequires>
    val exportsCount: UShort
    val exports: List<ModuleExports>
    val opensCount: UShort
    val opens: List<ModuleOpens>
    val usesCount: UShort
    val usesIndex: List<UShort>
    val providesCount: UShort
    val provides: List<ModuleProvides>

    fun moduleName(): String
    fun moduleVersion(): String
}
