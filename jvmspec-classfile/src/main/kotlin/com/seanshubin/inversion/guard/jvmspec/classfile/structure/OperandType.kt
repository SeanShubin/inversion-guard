package com.seanshubin.inversion.guard.jvmspec.classfile.structure

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

enum class OperandType {
    NONE,
    LOCAL_VARIABLE_INDEX,
    CONSTANT_POOL_INDEX,
    BYTE,
    BRANCH_OFFSET,
    BRANCH_OFFSET_WIDE,
    INDEX_CONST,
    CONSTANT_POOL_INDEX_THEN_TWO_ZEROES,
    CONSTANT_POOL_INDEX_THEN_COUNT_THEN_ZERO,
    CONSTANT_POOL_BYTE_SIZED_INDEX,
    LOOKUP_SWITCH,
    CONSTANT_POOL_INDEX_THEN_DIMENSIONS,
    ARRAY_TYPE,
    SHORT,
    TABLE_SWITCH,
    WIDE;
}
