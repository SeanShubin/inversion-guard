package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionIndexConst(
    override val opcode: OpCode,
    val index: Int,
    val const: Byte
) : Instruction {
    override fun complexity(): Int = 0

    companion object {
        val OPERAND_TYPE = OperandType.INDEX_CONST
        fun fromDataInput(opCode: OpCode, input: DataInput, codeIndex: Int): Instruction {
            val index = input.readUnsignedByte()
            val const = input.readByte()
            val instruction = InstructionIndexConst(opCode, index, const)
            return instruction
        }
    }
}
