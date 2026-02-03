package com.seanshubin.inversion.guard.analysis

import com.seanshubin.inversion.guard.jvmspec.model.api.JvmClass

interface JvmClassSelector {
    fun <T> flatMap(f: (JvmClass) -> List<T>): List<T>
}