package com.seanshubin.inversion.guard.console

import com.seanshubin.inversion.guard.composition.BootstrapDependencies
import com.seanshubin.inversion.guard.composition.Integrations
import kotlin.system.exitProcess

object InversionGuardApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val integrations: Integrations = ProductionIntegrations
        val exitCode = runApplication(args, integrations)
        exitProcess(exitCode)
    }

    fun runApplication(args: Array<String>, integrations: Integrations): Int {
        val bootstrapDependencies = BootstrapDependencies(args, integrations)
        bootstrapDependencies.runner.run()
        return if (bootstrapDependencies.errorCountHolder.errorCount > bootstrapDependencies.maximumAllowedErrorCount) 1 else 0
    }
}
