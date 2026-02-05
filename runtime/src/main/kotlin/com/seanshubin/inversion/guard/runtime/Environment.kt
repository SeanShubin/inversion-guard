package com.seanshubin.inversion.guard.runtime

import com.seanshubin.inversion.guard.di.contract.FilesContract

interface Environment {
    val files: FilesContract
}
