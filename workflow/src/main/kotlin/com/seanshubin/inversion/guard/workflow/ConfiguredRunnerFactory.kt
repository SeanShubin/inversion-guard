package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.jvmspec.rules.RuleLoader
import com.seanshubin.inversion.guard.jvmspec.runtime.application.Integrations
import java.nio.file.Path

class ConfiguredRunnerFactory<T>(
    private val args: Array<String>,
    private val createRunner: (Integrations, Configuration) -> T,
    private val ruleLoader: RuleLoader,
    private val integrations: Integrations,
) {
    fun createConfiguredRunner(): T {
        val configPathName = args.getOrNull(0) ?: "inversion-guard-config.json"
        val configPath = Path.of(configPathName)
        val configurationLoader = ConfigurationLoader(
            integrations,
            configPath,
            ruleLoader
        )
        val configuration = configurationLoader.load()
        return createRunner(integrations, configuration)
    }
}
