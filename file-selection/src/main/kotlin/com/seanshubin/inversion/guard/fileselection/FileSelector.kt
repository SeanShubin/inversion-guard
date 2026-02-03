package com.seanshubin.inversion.guard.fileselection

import java.nio.file.Path

interface FileSelector {
    fun <T> map(f: (Path) -> T): List<T>
    fun <T> flatMap(f: (Path) -> List<T>): List<T>
}
