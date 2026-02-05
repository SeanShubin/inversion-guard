package com.seanshubin.inversion.guard.runtime

import com.seanshubin.inversion.guard.di.contract.FilesContract

class EnvironmentImpl(
    override val files: FilesContract
) : Environment
