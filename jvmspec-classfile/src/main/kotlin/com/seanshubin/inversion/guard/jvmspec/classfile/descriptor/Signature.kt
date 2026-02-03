package com.seanshubin.inversion.guard.jvmspec.classfile.descriptor

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class Signature(
    val className: String,
    val methodName: String,
    val descriptor: Descriptor,
) {
    fun javaFormat(): String {
        return descriptor.javaFormat(className, methodName)
    }

    fun compactFormat(): String {
        return descriptor.compactFormat(className, methodName)
    }

    fun javaFormatUnqualified(): String {
        return descriptor.javaFormatUnqualified(className, methodName)
    }
}
