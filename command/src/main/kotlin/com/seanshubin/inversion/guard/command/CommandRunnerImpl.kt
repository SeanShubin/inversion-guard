package com.seanshubin.inversion.guard.command

class CommandRunnerImpl(
    private val environment: Environment,
    private val runCommandEvent: (Command) -> Unit
) : CommandRunner {
    override fun runCommand(command: Command) {
        runCommandEvent(command)
        command.run(environment)
    }
}