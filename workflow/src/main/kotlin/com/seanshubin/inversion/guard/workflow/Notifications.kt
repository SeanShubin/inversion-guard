package com.seanshubin.inversion.guard.workflow

import com.seanshubin.inversion.guard.command.Command

interface Notifications {
    fun timeTakenMillis(millis: Long)
    fun executingCommand(command: Command)
}
