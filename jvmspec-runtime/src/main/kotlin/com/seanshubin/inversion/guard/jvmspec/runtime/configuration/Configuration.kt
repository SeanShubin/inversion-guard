package com.seanshubin.inversion.guard.jvmspec.runtime.configuration

import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class Configuration(
    val inputDir: Path,
    val outputDir: Path,
    val include: List<String>,
    val exclude: List<String>
)
