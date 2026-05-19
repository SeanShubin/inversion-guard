package com.seanshubin.inversion.guard.composition

import com.seanshubin.inversion.guard.rules.JsonRuleLoader
import com.seanshubin.inversion.guard.rules.RuleLoader
import com.seanshubin.inversion.guard.workflow.ConfigurationLoader

class BootstrapDependencies(
    private val integrations: Integrations
) {
    private val files = integrations.files
    private val ruleLoader: RuleLoader = JsonRuleLoader()
    private val args = integrations.commandLineArgs
    private val configBaseName = args.getOrNull(0) ?: "inversion-guard"
    val configurationLoader = ConfigurationLoader(configBaseName, files, ruleLoader)
}
