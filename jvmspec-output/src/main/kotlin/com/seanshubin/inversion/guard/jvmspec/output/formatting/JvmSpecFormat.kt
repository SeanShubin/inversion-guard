package com.seanshubin.inversion.guard.jvmspec.output.formatting

import com.seanshubin.inversion.guard.jvmspec.infrastructure.collections.Tree
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.api.JvmInstruction

//
// This file was imported from: ../jvmspec
// Module: output
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmSpecFormat {
    fun classTreeList(jvmClass: JvmClass): List<Tree>
    fun instructionTree(jvmInstruction: JvmInstruction): Tree
}
