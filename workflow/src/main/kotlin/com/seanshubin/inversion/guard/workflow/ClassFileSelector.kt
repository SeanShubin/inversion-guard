package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ClassFile

interface ClassFileSelector {
    fun <T> flatMap(f: (ClassFile) -> List<T>): List<T>
}
