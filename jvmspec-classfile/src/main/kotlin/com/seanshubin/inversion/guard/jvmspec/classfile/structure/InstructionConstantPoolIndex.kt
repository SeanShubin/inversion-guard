package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionConstantPoolIndex(
    override val opcode: OpCode,
    val constantPoolIndex: UShort
) : Instruction {
    override fun complexity(): Int = 0

    companion object {
        val OPERAND_TYPE = OperandType.CONSTANT_POOL_INDEX
        fun fromDataInput(opCode: OpCode, input: DataInput, index: Int): Instruction {
            val constantPoolIndex = input.readUnsignedShort().toUShort()
            val instruction = InstructionConstantPoolIndex(opCode, constantPoolIndex)
            return instruction
        }
    }
}
