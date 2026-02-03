package com.seanshubin.inversion.guard.jvmspec.classfile.specification

import com.seanshubin.inversion.guard.jvmspec.infrastructure.formatting.DataFormat.toHex

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

enum class ArrayType(val code: Byte) {
    BOOLEAN(4),
    CHAR(5),
    FLOAT(6),
    DOUBLE(7),
    BYTE(8),
    SHORT(9),
    INT(10),
    LONG(11);

    fun line(): String = "$name($code)"

    companion object {
        fun fromByte(byte: Byte): ArrayType {
            return entries.firstOrNull { it.code == byte }
                ?: throw IllegalArgumentException("Unknown array type code: 0x${byte.toHex()}")
        }
    }
}
