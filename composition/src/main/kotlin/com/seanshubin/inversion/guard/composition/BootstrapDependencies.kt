package com.seanshubin.inversion.guard.composition

import com.seanshubin.inversion.guard.runtime.ErrorCountHolder
import com.seanshubin.inversion.guard.workflow.ConfigurationLoader
import com.seanshubin.inversion.guard.jvmspec.rules.JsonRuleLoader
import com.seanshubin.inversion.guard.jvmspec.rules.RuleLoader
import java.nio.file.Path

class BootstrapDependencies(
    private val args: Array<String>,
    private val integrations: Integrations
) {
    private val files = integrations.files
    private val ruleLoader: RuleLoader = JsonRuleLoader()
    private val configPathName = args.getOrNull(0) ?: "inversion-guard-config.json"
    private val configPath = Path.of(configPathName)
    private val configurationLoader = ConfigurationLoader(files, configPath, ruleLoader)
    private val configuration = configurationLoader.load()
    private val applicationDependencies: ApplicationDependencies = ApplicationDependencies(integrations, configuration)
    val runner: Runnable = applicationDependencies.runner
    val errorCountHolder: ErrorCountHolder = applicationDependencies.errorCountHolder
    val maximumAllowedErrorCount: Int = applicationDependencies.maximumAllowedErrorCount
}
