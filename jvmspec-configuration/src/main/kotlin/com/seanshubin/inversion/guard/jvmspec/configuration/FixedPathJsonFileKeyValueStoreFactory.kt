package com.seanshubin.inversion.guard.jvmspec.configuration

import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: configuration
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface FixedPathJsonFileKeyValueStoreFactory {
    fun create(path: Path): KeyValueStore
}
