package com.seanshubin.inversion.guard.analysis

import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass
import com.seanshubin.inversion.guard.jvmspec.model.conversion.Converter

class JvmClassSelectorImpl(
    private val classSelector: ClassSelector,
    private val converter: Converter
) : JvmClassSelector {
    override fun <T> flatMap(f: (JvmClass) -> List<T>): List<T> {
        return classSelector.flatMap { classFile ->
            val jvmClass = with(converter) { classFile.toJvmClass() }
            f(jvmClass)
        }
    }
}
