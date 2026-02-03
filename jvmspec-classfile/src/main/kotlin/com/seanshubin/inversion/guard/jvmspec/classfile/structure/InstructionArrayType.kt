package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import com.seanshubin.inversion.guard.jvmspec.classfile.specification.ArrayType
import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionArrayType(
    override val opcode: OpCode,
    val arrayType: ArrayType
) : Instruction {
    override fun complexity(): Int = 0

    companion object {
        val OPERAND_TYPE = OperandType.ARRAY_TYPE
        fun fromDataInput(opCode: OpCode, input: DataInput, codeIndex: Int): Instruction {
            val arrayTypeValue = input.readByte()
            val arrayType = ArrayType.fromByte(arrayTypeValue)
            val instruction = InstructionArrayType(opCode, arrayType)
            return instruction
        }
    }
}
