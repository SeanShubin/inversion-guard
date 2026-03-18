package com.seanshubin.inversion.guard.composition

import com.seanshubin.inversion.guard.workflow.RunnerSummary

object CompositionPipeline {
    fun orchestrateStages(integrations: Integrations): RunnerSummary {
        // Stage 1: Bootstrap - wire then work
        val bootstrapDeps = BootstrapDependencies(integrations)  // wiring
        val configuration = bootstrapDeps.configurationLoader.load()  // work

        // Stage 2: Application - wire then work
        val appDeps = ApplicationDependencies(integrations, configuration)  // wiring
        val runnerSummary = appDeps.runner.run()  // work
        return runnerSummary
    }
}
