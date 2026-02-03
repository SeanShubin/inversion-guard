package com.seanshubin.inversion.guard.analysis

import com.seanshubin.inversion.guard.jvmspec.classfile.descriptor.Signature

data class InvocationAnalysis(
    val className: String,
    val methodName: String,
    val signature: Signature,
    val invocationOpcodeName: String,
    val invocationType: InvocationType
)
