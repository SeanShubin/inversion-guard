package com.seanshubin.inversion.guard.jvmspec.infrastructure.command

//
// This file was imported from: ../jvmspec
// Module: infrastructure
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface CommandRunner {
    fun run(command: Command)
}
