package com.seanshubin.inversion.guard.command

import com.seanshubin.inversion.guard.jvmspec.contract.FilesContract

interface Environment {
    val files: FilesContract
}
