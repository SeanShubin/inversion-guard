package com.seanshubin.inversion.guard.appconfig

import com.seanshubin.inversion.guard.jvmspec.contract.FilesContract

class EnvironmentImpl(
    override val files: FilesContract
) : Environment
