package com.seanshubin.inversion.guard.jvmspec.runtime.configuration

import com.seanshubin.inversion.guard.jvmspec.infrastructure.types.TypeSafety.toTypedList
import com.seanshubin.inversion.guard.jvmspec.runtime.storage.KeyValueStore
import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class ConfigurationReader(
    private val keyValueStore: KeyValueStore,
    private val createFromConfig: (Configuration) -> Runnable
) : Runnable {
    override fun run() {
        val inputDirName = keyValueStore.load(listOf("inputDir")) as String
        val inputDir = Path.of(inputDirName)
        val outputDirName = keyValueStore.load(listOf("outputDir")) as String
        val outputDir = Path.of(outputDirName)
        val include = keyValueStore.load(listOf("includeFile")).toTypedList<String>()
        val exclude = keyValueStore.load(listOf("excludeFile")).toTypedList<String>()
        val configuration = Configuration(
            inputDir,
            outputDir,
            include,
            exclude
        )
        createFromConfig(configuration).run()
    }
}
