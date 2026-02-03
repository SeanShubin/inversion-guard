package com.seanshubin.inversion.guard.analysis

import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass

interface ClassAnalyzer {
    fun analyzeClass(jvmClass: JvmClass): ClassAnalysis
}
