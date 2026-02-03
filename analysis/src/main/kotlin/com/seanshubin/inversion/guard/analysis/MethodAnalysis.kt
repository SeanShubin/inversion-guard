package com.seanshubin.inversion.guard.analysis

import com.seanshubin.inversion.guard.jvmspec.classfile.descriptor.Signature

data class MethodAnalysis(
    val className: String,
    val methodName: String,
    val signature: Signature,
    val complexity: Int,
    val boundaryLogicCategories: Set<String>,
    val staticInvocations: List<InvocationAnalysis>
) {
    fun countStaticInvocations(invocationType: InvocationType): Int {
        return staticInvocations.count { it.invocationType == invocationType }
    }

    fun isBoundaryLogic(): Boolean {
        return boundaryLogicCategories.isNotEmpty()
    }
}