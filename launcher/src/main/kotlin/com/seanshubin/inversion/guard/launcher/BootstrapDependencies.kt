package com.seanshubin.inversion.guard.launcher

import com.seanshubin.inversion.guard.jvmspec.configuration.FixedPathJsonFileKeyValueStoreFactory
import com.seanshubin.inversion.guard.jvmspec.configuration.FixedPathJsonFileKeyValueStoreFactoryImpl
import com.seanshubin.inversion.guard.workflow.ConfiguredRunnerFactory
import com.seanshubin.inversion.guard.jvmspec.rules.JsonRuleLoader
import com.seanshubin.inversion.guard.jvmspec.rules.RuleLoader
import com.seanshubin.inversion.guard.jvmspec.runtime.application.Integrations

class BootstrapDependencies(
    private val args: Array<String>,
    private val integrations: Integrations
) {
    val keyValueStoreFactory: FixedPathJsonFileKeyValueStoreFactory =
        FixedPathJsonFileKeyValueStoreFactoryImpl(integrations.files)
    val ruleLoader: RuleLoader = JsonRuleLoader()
    val configuredRunnerFactory: ConfiguredRunnerFactory = ConfiguredRunnerFactory(
        args,
        ApplicationDependencies::fromConfiguration,
        keyValueStoreFactory,
        ruleLoader,
        integrations
    )
    val runner: Runnable = configuredRunnerFactory.createConfiguredRunner()
}
