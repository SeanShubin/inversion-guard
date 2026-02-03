package com.seanshubin.inversion.guard.jvmspec.runtime.application

import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface Notifications : ReportGenerator.Events {
    override fun processingFile(inputFile: Path, outputDir: Path)
    override fun timeTakenMillis(millis: Long)
}
