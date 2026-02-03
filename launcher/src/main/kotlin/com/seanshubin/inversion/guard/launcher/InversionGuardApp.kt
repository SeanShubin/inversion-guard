package com.seanshubin.inversion.guard.launcher

import com.seanshubin.inversion.guard.jvmspec.runtime.application.Integrations
import com.seanshubin.inversion.guard.jvmspec.runtime.application.ProductionIntegrations

object InversionGuardApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val integrations: Integrations = ProductionIntegrations
        BootstrapDependencies(args, integrations).runner.run()
    }
}