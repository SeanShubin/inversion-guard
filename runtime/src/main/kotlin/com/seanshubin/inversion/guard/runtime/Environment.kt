package com.seanshubin.inversion.guard.runtime

import com.seanshubin.inversion.guard.jvmspec.contract.FilesContract

interface Environment {
    val files: FilesContract
}
