package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class InstructionLookupSwitch(
    override val opcode: OpCode,
    val padding: List<Byte>,
    val default: Int,
    val nPairs: Int,
    val pairs: List<MatchOffset>,
) : Instruction {
    override fun complexity(): Int = pairs.size

    companion object {
        val OPERAND_TYPE = OperandType.LOOKUP_SWITCH
        fun fromDataInput(opCode: OpCode, input: DataInput, index: Int): Instruction {
            val paddingSize = (-index - 1).mod(4)
            val padding = List(paddingSize) { input.readByte() }
            val default = input.readInt()
            val nPairs = input.readInt()
            val pairs = List(nPairs) {
                val match = input.readInt()
                val offset = input.readInt()
                MatchOffset(match, offset)
            }
            return InstructionLookupSwitch(
                opcode = opCode,
                padding = padding,
                default = default,
                nPairs = nPairs,
                pairs = pairs
            )
        }
    }
}
