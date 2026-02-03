package com.seanshubin.inversion.guard.jvmspec.infrastructure.types

//
// This file was imported from: ../jvmspec
// Module: infrastructure
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

object TypeSafety {
    inline fun <reified T> Any?.toTypedList(): List<T> {
        return (this as List<*>).map { it as T }
    }
}
