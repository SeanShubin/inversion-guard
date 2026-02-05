package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.jvmspec.configuration.FixedPathJsonFileKeyValueStoreFactory
import com.seanshubin.inversion.guard.jvmspec.infrastructure.types.TypeSafety.toTypedList
import com.seanshubin.inversion.guard.jvmspec.rules.RuleLoader
import com.seanshubin.inversion.guard.jvmspec.runtime.application.Integrations
import java.nio.file.Path

class ConfiguredRunnerFactory<T>(
    private val args: Array<String>,
    private val createRunner: (Integrations, Configuration) -> T,
    private val keyValueStoreFactory: FixedPathJsonFileKeyValueStoreFactory,
    private val ruleLoader: RuleLoader,
    private val integrations: Integrations,
) {
    fun createConfiguredRunner(): T {
        val configPathName = args.getOrNull(0) ?: "inversion-guard-config.json"
        val configPath = Path.of(configPathName)
        val keyValueStore = keyValueStoreFactory.create(configPath)
        val baseDirName = keyValueStore.loadOrCreateDefault(listOf("inputDir"), ".") as String
        val outputDirName =
            keyValueStore.loadOrCreateDefault(listOf("outputDir"), "generated/inversion-guard-report") as String
        val includeFile =
            keyValueStore.loadOrCreateDefault(
                listOf("classes", "includeFile"),
                listOf(".*/target/.*\\.class")
            ).toTypedList<String>()
        val excludeFile =
            keyValueStore.loadOrCreateDefault(listOf("classes", "excludeFile"), emptyList<String>())
                .toTypedList<String>()
        val skipDir =
            keyValueStore.loadOrCreateDefault(listOf("classes", "skipDir"), emptyList<String>()).toTypedList<String>()
        val rulesFileName =
            keyValueStore.loadOrCreateDefault(listOf("globalRules"), "inversion-guard-rules.json") as String
        val localCore =
            keyValueStore.loadOrCreateDefault(listOf("localRules", "core"), emptyList<String>()).toTypedList<String>()
        val localBoundary =
            keyValueStore.loadOrCreateDefault(listOf("localRules", "boundary"), emptyList<String>())
                .toTypedList<String>()
        val failOnUnknown = keyValueStore.loadOrCreateDefault(listOf("failOnUnknown"), false) as Boolean
        val maximumAllowedErrorCount = keyValueStore.loadOrCreateDefault(listOf("maximumAllowedErrorCount"), 0) as Int
        val baseDir = Path.of(baseDirName)
        val outputDir = Path.of(outputDirName)
        val rulesFile = Path.of(rulesFileName)
        if (!integrations.files.exists(rulesFile)) {
            throw RuntimeException(
                "Global rules file not found: ${rulesFile.toAbsolutePath()}\n" +
                        "  This file is required for inversion guard analysis.\n" +
                        "  Configured via: 'globalRules' key in config (current value: '$rulesFileName')\n" +
                        "  To fix: Create the rules file at the specified location, or update the 'globalRules' config to point to an existing file."
            )
        }
        val rulesJson = integrations.files.readString(rulesFile)
        val rulesData = ruleLoader.load(rulesJson)
        val categories = rulesData.categories
        val globalCore = rulesData.core
        val globalBoundary = rulesData.boundary
        val core = localCore + globalCore
        val boundary = localBoundary + globalBoundary
        val configuration = Configuration(
            baseDir,
            outputDir,
            includeFile,
            excludeFile,
            skipDir,
            core,
            boundary,
            localCore,
            localBoundary,
            failOnUnknown,
            categories,
            maximumAllowedErrorCount
        )
        return createRunner(integrations, configuration)
    }
}
