package com.seanshubin.inversion.guard.jvmspec.infrastructure.time

import java.time.Clock

//
// This file was imported from: ../jvmspec
// Module: infrastructure
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class Timer(private val clock: Clock) {
    fun withTimerMilliseconds(f: () -> Unit): Long {
        val startTime = clock.millis()
        f()
        val endTime = clock.millis()
        val durationMillis = endTime - startTime
        return durationMillis
    }
}
