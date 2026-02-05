package com.seanshubin.inversion.guard.jvmspec.infrastructure.command

import com.seanshubin.inversion.guard.di.contract.FilesContract

//
// This file was imported from: ../jvmspec
// Module: infrastructure
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface Environment {
    val files: FilesContract
}
