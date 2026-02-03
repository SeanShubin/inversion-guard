package com.seanshubin.inversion.guard.jvmspec.configuration

//
// This file was imported from: ../jvmspec
// Module: configuration
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface KeyValueStore {
    fun load(key: List<Any>): Any?
    fun store(key: List<Any>, value: Any?)
    fun exists(key: List<Any>): Boolean
    fun arraySize(key: List<Any>): Int
    fun loadOrDefault(key: List<Any>, default: Any?): Any? =
        if (exists(key)) load(key) else default

    fun loadOrCreateDefault(key: List<Any>, default: Any?): Any? =
        if (exists(key)) load(key) else default.also { store(key, it) }
}
