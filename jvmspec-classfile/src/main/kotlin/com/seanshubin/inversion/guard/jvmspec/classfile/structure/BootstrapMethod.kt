package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class BootstrapMethod(
    val bootstrapMethodRef: UShort,
    val numBootstrapArguments: UShort,
    val bootstrapArguments: List<UShort>
) {
    companion object {
        fun fromDataInput(input: DataInput): BootstrapMethod {
            val bootstrapMethodRef = input.readUnsignedShort().toUShort()
            val numBootstrapArguments = input.readUnsignedShort().toUShort()
            val bootstrapArguments = (0 until numBootstrapArguments.toInt()).map {
                input.readUnsignedShort().toUShort()
            }
            return BootstrapMethod(bootstrapMethodRef, numBootstrapArguments, bootstrapArguments)
        }
    }
}
