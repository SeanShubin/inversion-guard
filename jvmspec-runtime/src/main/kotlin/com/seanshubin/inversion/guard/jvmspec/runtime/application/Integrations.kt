package com.seanshubin.inversion.guard.jvmspec.runtime.application

import com.seanshubin.inversion.guard.jvmspec.contract.FilesContract
import java.time.Clock

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface Integrations {
    val files: FilesContract
    val clock: Clock
    val emit: (Any?) -> Unit
}
