package com.seanshubin.inversion.guard.composition

import com.seanshubin.inversion.guard.runtime.ErrorCountHolder
import com.seanshubin.inversion.guard.workflow.ConfiguredRunnerFactory
import com.seanshubin.inversion.guard.jvmspec.rules.JsonRuleLoader
import com.seanshubin.inversion.guard.jvmspec.rules.RuleLoader
import com.seanshubin.inversion.guard.jvmspec.runtime.application.Integrations

class BootstrapDependencies(
    private val args: Array<String>,
    private val integrations: Integrations
) {
    private val ruleLoader: RuleLoader = JsonRuleLoader()
    private val configuredRunnerFactory: ConfiguredRunnerFactory<ApplicationDependencies> = ConfiguredRunnerFactory(
        args,
        ApplicationDependencies::fromConfiguration,
        ruleLoader,
        integrations
    )
    private val applicationDependencies: ApplicationDependencies = configuredRunnerFactory.createConfiguredRunner()
    val runner: Runnable = applicationDependencies.runner
    val errorCountHolder: ErrorCountHolder = applicationDependencies.errorCountHolder
    val maximumAllowedErrorCount: Int = applicationDependencies.maximumAllowedErrorCount
}
