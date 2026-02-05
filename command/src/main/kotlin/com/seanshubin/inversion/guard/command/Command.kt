package com.seanshubin.inversion.guard.command

import com.seanshubin.inversion.guard.runtime.Environment

interface Command {
    fun run(environment: Environment)
}