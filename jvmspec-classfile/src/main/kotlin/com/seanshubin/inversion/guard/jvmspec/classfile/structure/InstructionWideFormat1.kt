package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionWideFormat1(
    override val opcode: OpCode,
    val modifiedOpCode: OpCode,
    val localVariableIndex: UShort
) : Instruction {
    override fun complexity(): Int = 0

    companion object {
        fun fromDataInput(opCode: OpCode, modifiedOpCode: OpCode, input: DataInput, index: Int): Instruction {
            val localVariableIndex = input.readUnsignedShort().toUShort()
            return InstructionWideFormat1(
                opcode = opCode,
                modifiedOpCode = modifiedOpCode,
                localVariableIndex = localVariableIndex
            )
        }
    }
}
