package com.seanshubin.inversion.guard.command

interface Command {
    fun run(environment: Environment)
}