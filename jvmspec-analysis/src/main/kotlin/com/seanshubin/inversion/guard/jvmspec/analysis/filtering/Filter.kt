package com.seanshubin.inversion.guard.jvmspec.analysis.filtering

//
// This file was imported from: ../jvmspec
// Module: analysis
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface Filter {
    fun match(text: String): Set<String>
}
