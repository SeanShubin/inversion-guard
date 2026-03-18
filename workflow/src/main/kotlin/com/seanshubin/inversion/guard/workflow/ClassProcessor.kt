package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.analysis.ClassAnalysis
import com.seanshubin.inversion.guard.command.Command

interface ClassProcessor {
    fun processClass(classAnalysis: ClassAnalysis): List<Command>
}