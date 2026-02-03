package com.seanshubin.inversion.guard.jvmspec.classfile.structure

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface Instruction {
    val opcode: OpCode
    fun complexity(): Int
}
