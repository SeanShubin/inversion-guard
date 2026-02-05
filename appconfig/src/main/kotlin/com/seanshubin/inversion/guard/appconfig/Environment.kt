package com.seanshubin.inversion.guard.appconfig

import com.seanshubin.inversion.guard.jvmspec.contract.FilesContract

interface Environment {
    val files: FilesContract
}
