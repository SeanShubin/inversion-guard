package com.seanshubin.inversion.guard.jvmspec.runtime.application

import com.seanshubin.inversion.guard.di.contract.FilesContract
import com.seanshubin.inversion.guard.di.delegate.FilesDelegate
import java.time.Clock

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

object ProductionIntegrations : Integrations {
    override val files: FilesContract = FilesDelegate.defaultInstance()
    override val clock: Clock = Clock.systemUTC()
    override val emit: (Any?) -> Unit = ::println
}
