package com.seanshubin.inversion.guard.jvmspec.infrastructure.filesystem

import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: infrastructure
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

object PathUtil {
    fun Path.removeExtension(expectedExtension: String): String {
        val asString = this.toString()
        if (!asString.endsWith(".$expectedExtension")) {
            throw IllegalArgumentException("Path does not end with extension '$expectedExtension'")
        }
        val withoutExtension = asString.dropLast(expectedExtension.length + 1)
        return withoutExtension
    }
}
