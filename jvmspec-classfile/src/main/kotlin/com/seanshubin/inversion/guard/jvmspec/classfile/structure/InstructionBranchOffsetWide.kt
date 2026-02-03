package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionBranchOffsetWide(
    override val opcode: OpCode,
    val offset: Int,
) : Instruction {
    override fun complexity(): Int = 1

    companion object {
        val OPERAND_TYPE = OperandType.BRANCH_OFFSET_WIDE
        fun fromDataInput(opCode: OpCode, input: DataInput, index: Int): Instruction {
            val offset = input.readInt()
            return InstructionBranchOffsetWide(
                opcode = opCode,
                offset = offset
            )
        }
    }
}
