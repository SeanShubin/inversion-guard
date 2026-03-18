package com.seanshubin.inversion.guard.composition

import com.seanshubin.inversion.guard.jvmspec.rules.JsonRuleLoader
import com.seanshubin.inversion.guard.jvmspec.rules.RuleLoader
import com.seanshubin.inversion.guard.workflow.ConfigurationLoader

class BootstrapDependencies(
    private val integrations: Integrations
) {
    private val files = integrations.files
    private val ruleLoader: RuleLoader = JsonRuleLoader()
    private val args = integrations.commandLineArgs
    val configurationLoader = ConfigurationLoader(args, files, ruleLoader)
}
