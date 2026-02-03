package com.seanshubin.inversion.guard.jvmspec.runtime.storage

//
// This file was imported from: ../jvmspec
// Module: runtime
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface KeyValueStore {
    fun load(key: List<Any>): Any?
    fun store(key: List<Any>, value: Any?)
    fun exists(key: List<Any>): Boolean
    fun arraySize(key: List<Any>): Int
}
