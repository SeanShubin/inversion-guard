package com.seanshubin.inversion.guard.jvmspec.configuration

import com.seanshubin.inversion.guard.jvmspec.contract.FilesContract
import java.nio.file.Path

//
// This file was imported from: ../jvmspec
// Module: configuration
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class FixedPathJsonFileKeyValueStoreFactoryImpl(val files: FilesContract) : FixedPathJsonFileKeyValueStoreFactory {
    override fun create(path: Path): KeyValueStore {
        return FixedPathJsonFileKeyValueStore(files, path)
    }
}
