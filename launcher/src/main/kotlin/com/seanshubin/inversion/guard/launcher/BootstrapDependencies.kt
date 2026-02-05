package com.seanshubin.inversion.guard.launcher

import com.seanshubin.inversion.guard.jvmspec.configuration.FixedPathJsonFileKeyValueStoreFactory
import com.seanshubin.inversion.guard.jvmspec.configuration.FixedPathJsonFileKeyValueStoreFactoryImpl
import com.seanshubin.inversion.guard.appconfig.ApplicationRunner
import com.seanshubin.inversion.guard.appconfig.ErrorCountHolder
import com.seanshubin.inversion.guard.workflow.ConfiguredRunnerFactory
import com.seanshubin.inversion.guard.jvmspec.rules.JsonRuleLoader
import com.seanshubin.inversion.guard.jvmspec.rules.RuleLoader
import com.seanshubin.inversion.guard.jvmspec.runtime.application.Integrations

class BootstrapDependencies(
    private val args: Array<String>,
    private val integrations: Integrations
) {
    private val keyValueStoreFactory: FixedPathJsonFileKeyValueStoreFactory =
        FixedPathJsonFileKeyValueStoreFactoryImpl(integrations.files)
    private val ruleLoader: RuleLoader = JsonRuleLoader()
    private val configuredRunnerFactory: ConfiguredRunnerFactory = ConfiguredRunnerFactory(
        args,
        ApplicationDependencies::fromConfiguration,
        keyValueStoreFactory,
        ruleLoader,
        integrations
    )
    private val applicationRunner: ApplicationRunner = configuredRunnerFactory.createConfiguredRunner()
    val runner: Runnable = applicationRunner.runner
    val errorCountHolder: ErrorCountHolder = applicationRunner.errorCountHolder
    val maximumAllowedErrorCount: Int = applicationRunner.maximumAllowedErrorCount
}
