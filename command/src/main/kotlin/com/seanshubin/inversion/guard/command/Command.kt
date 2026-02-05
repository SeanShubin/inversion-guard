package com.seanshubin.inversion.guard.command

import com.seanshubin.inversion.guard.appconfig.Environment

interface Command {
    fun run(environment: Environment)
}