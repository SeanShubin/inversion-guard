package com.seanshubin.inversion.guard.jvmspec.runtime.application

import com.seanshubin.inversion.guard.di.contract.FilesContract
import com.seanshubin.inversion.guard.jvmspec.model.api.*
import com.seanshubin.inversion.guard.jvmspec.model.implementation.*
import com.seanshubin.inversion.guard.jvmspec.output.formatting.JvmSpecFormat
import com.seanshubin.inversion.guard.jvmspec.output.formatting.JvmSpecFormatDetailed
import java.nio.file.Path
import java.nio.file.Paths

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class Dependencies(
    integrations: Integrations,
    classFilePathString: String
) {
    private val files: FilesContract = integrations.files
    private val emit: (Any?) -> Unit = integrations.emit
    private val classFilePath: Path = Paths.get(classFilePathString)
    private val format: JvmSpecFormat = JvmSpecFormatDetailed()
    private val attributeFactory: JvmAttributeFactory = JvmAttributeFactoryImpl()
    private val methodFactory: JvmMethodFactory = JvmMethodFactoryImpl(attributeFactory)
    private val fieldFactory: JvmFieldFactory = JvmFieldFactoryImpl(attributeFactory)
    private val classFactory: JvmClassFactory = JvmClassFactoryImpl(methodFactory, fieldFactory, attributeFactory)
    val runner: Runnable = Runner(
        files,
        emit,
        classFilePath,
        classFactory,
        format
    )
}
