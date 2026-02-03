package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionLocalVariableIndex(
    override val opcode: OpCode,
    val localVariableIndex: Int
) : Instruction {
    override fun complexity(): Int = 0

    companion object {
        val OPERAND_TYPE = OperandType.LOCAL_VARIABLE_INDEX
        fun fromDataInput(opCode: OpCode, input: DataInput, index: Int): Instruction {
            val localVariableIndex = input.readUnsignedByte()
            val instruction = InstructionLocalVariableIndex(opCode, localVariableIndex)
            return instruction
        }
    }
}
