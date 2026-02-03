package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionByte(
    override val opcode: OpCode,
    val value: Byte
) : Instruction {
    override fun complexity(): Int = 0

    companion object {
        val OPERAND_TYPE = OperandType.BYTE
        fun fromDataInput(opCode: OpCode, input: DataInput, codeIndex: Int): Instruction {
            val value = input.readByte()
            val instruction = InstructionByte(opCode, value)
            return instruction
        }
    }
}
