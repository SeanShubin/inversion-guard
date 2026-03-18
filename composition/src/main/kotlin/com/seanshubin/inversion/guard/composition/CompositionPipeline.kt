package com.seanshubin.inversion.guard.composition

object CompositionPipeline {
    fun orchestrateStages(integrations: Integrations) {
        // Stage 1: Bootstrap - wire then work
        val bootstrapDeps = BootstrapDependencies(integrations)  // wiring
        val configuration = bootstrapDeps.configurationLoader.load()  // work

        // Stage 2: Application - wire then work
        val appDeps = ApplicationDependencies(integrations, configuration)  // wiring
        appDeps.runner.run()  // work
    }

    fun executeReturningExitCode(integrations: Integrations): Int {
        return executeConvertingExceptionsToExitCode(integrations) {
            orchestrateStages(integrations)
        }
    }

    private fun executeConvertingExceptionsToExitCode(
        integrations: Integrations,
        operation: () -> Unit
    ): Int {
        return try {
            operation()
            integrations.getExitCode()
        } catch (e: Exception) {
            integrations.systemErr.println("Unexpected error: ${e.message}")
            e.printStackTrace(integrations.systemErr)
            1
        }
    }
}
