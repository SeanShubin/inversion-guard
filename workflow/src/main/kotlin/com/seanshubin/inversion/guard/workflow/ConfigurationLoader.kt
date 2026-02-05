package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.dynamic.core.KeyValueStore
import com.seanshubin.inversion.guard.dynamic.core.KeyValueStoreWithDocumentation
import com.seanshubin.inversion.guard.dynamic.core.KeyValueStoreWithDocumentationDelegate
import com.seanshubin.inversion.guard.dynamic.json.JsonFileKeyValueStore
import com.seanshubin.inversion.guard.jvmspec.infrastructure.types.TypeSafety.toTypedList
import com.seanshubin.inversion.guard.jvmspec.rules.RuleLoader
import com.seanshubin.inversion.guard.jvmspec.runtime.application.Integrations
import java.nio.file.Path
import java.nio.file.Paths

class ConfigurationLoader(
    private val integrations: Integrations,
    private val configPath: Path,
    private val ruleLoader: RuleLoader
) {
    fun load(): Configuration {
        val configBaseName = configPath.toString().removeSuffix("-config.json").removeSuffix(".json")
        val configDocumentationFile = Paths.get("$configBaseName-documentation.json")

        val keyValueStore: KeyValueStore = JsonFileKeyValueStore(integrations.files, configPath)
        val documentationKeyValueStore: KeyValueStore = JsonFileKeyValueStore(integrations.files, configDocumentationFile)
        val config: KeyValueStoreWithDocumentation =
            KeyValueStoreWithDocumentationDelegate(keyValueStore, documentationKeyValueStore)

        // Initialize _documentation metadata
        config.load(
            listOf("_documentation", "description"),
            "Analyzes dependencies between classes to detect architectural violations",
            ConfigDocumentation.documentationDescription
        )
        config.load(
            listOf("_documentation", "readme"),
            "https://github.com/SeanShubin/inversion-guard/blob/master/README.md",
            ConfigDocumentation.documentationReadme
        )
        config.load(
            listOf("_documentation", "configHelp"),
            "$configBaseName-documentation.json",
            ConfigDocumentation.documentationConfigHelp
        )

        val baseDirName = config.load(listOf("inputDir"), ".", ConfigDocumentation.inputDir) as String
        val outputDirName = config.load(
            listOf("outputDir"),
            "generated/inversion-guard",
            ConfigDocumentation.outputDir
        ) as String
        val includeFile = config.load(
            listOf("classes", "includeFile"),
            listOf(".*/target/.*\\.class"),
            ConfigDocumentation.classesIncludeFile
        ).toTypedList<String>()
        val excludeFile = config.load(
            listOf("classes", "excludeFile"),
            emptyList<String>(),
            ConfigDocumentation.classesExcludeFile
        ).toTypedList<String>()
        val skipDir = config.load(
            listOf("classes", "skipDir"),
            emptyList<String>(),
            ConfigDocumentation.classesSkipDir
        ).toTypedList<String>()
        val rulesFileName = config.load(
            listOf("globalRules"),
            "inversion-guard-rules.json",
            ConfigDocumentation.globalRules
        ) as String
        val localCore = config.load(
            listOf("localRules", "core"),
            emptyList<String>(),
            ConfigDocumentation.localRulesCore
        ).toTypedList<String>()
        val localBoundary = config.load(
            listOf("localRules", "boundary"),
            emptyList<String>(),
            ConfigDocumentation.localRulesBoundary
        ).toTypedList<String>()
        val failOnUnknown = config.load(
            listOf("failOnUnknown"),
            false,
            ConfigDocumentation.failOnUnknown
        ) as Boolean
        val maximumAllowedErrorCount = config.load(
            listOf("maximumAllowedErrorCount"),
            0,
            ConfigDocumentation.maximumAllowedErrorCount
        ) as Int

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

        return Configuration(
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
    }
}
