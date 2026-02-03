package com.seanshubin.inversion.guard.analysis

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ClassFile

interface ClassSelector {
    fun <T> flatMap(f: (ClassFile) -> List<T>): List<T>
}
