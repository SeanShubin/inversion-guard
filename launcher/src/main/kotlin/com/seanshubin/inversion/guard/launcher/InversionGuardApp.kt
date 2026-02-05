package com.seanshubin.inversion.guard.launcher

import com.seanshubin.inversion.guard.jvmspec.runtime.application.Integrations
import com.seanshubin.inversion.guard.jvmspec.runtime.application.ProductionIntegrations
import kotlin.system.exitProcess

object InversionGuardApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val integrations: Integrations = ProductionIntegrations
        val bootstrapDependencies = BootstrapDependencies(args, integrations)
        bootstrapDependencies.runner.run()
        val exitCode = if (bootstrapDependencies.errorCountHolder.errorCount > bootstrapDependencies.maximumAllowedErrorCount) 1 else 0
        exitProcess(exitCode)
    }
}