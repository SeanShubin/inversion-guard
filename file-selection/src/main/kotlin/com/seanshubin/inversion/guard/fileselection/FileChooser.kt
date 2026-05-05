package com.seanshubin.inversion.guard.fileselection

import java.nio.file.Path

interface FileChooser {
    fun choose(fileSelection: FileSelection): List<Path>
}
