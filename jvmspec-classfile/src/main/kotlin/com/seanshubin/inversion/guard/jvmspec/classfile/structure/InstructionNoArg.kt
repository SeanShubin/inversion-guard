package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionNoArg(
    override val opcode: OpCode
) : Instruction {
    override fun complexity(): Int = 0

    companion object {
        val OPERAND_TYPE = OperandType.NONE
        fun fromDataInput(opCode: OpCode, input: DataInput, index: Int): Instruction {
            return InstructionNoArg(opCode)
        }
    }
}
