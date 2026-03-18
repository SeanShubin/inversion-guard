package com.seanshubin.inversion.guard.console

import com.seanshubin.inversion.guard.composition.CompositionPipeline
import com.seanshubin.inversion.guard.composition.Integrations
import kotlin.system.exitProcess

object InversionGuardApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val integrations: Integrations = ProductionIntegrations(args)
        val exitCode = CompositionPipeline.executeReturningExitCode(integrations)
        exitProcess(exitCode)
    }
}
