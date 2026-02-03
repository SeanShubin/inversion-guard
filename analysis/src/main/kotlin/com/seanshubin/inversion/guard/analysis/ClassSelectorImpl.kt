package com.seanshubin.inversion.guard.analysis

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ClassFile

class ClassSelectorImpl(

) : ClassSelector {
    override fun <T> flatMap(f: (ClassFile) -> List<T>): List<T> {
        throw UnsupportedOperationException("Not Implemented!")
    }
}