package com.seanshubin.inversion.guard.console

import com.seanshubin.inversion.guard.composition.CompositionPipeline
import com.seanshubin.inversion.guard.composition.Integrations
import com.seanshubin.inversion.guard.workflow.RunnerSummary

object TopLevelExceptionHandler {
    fun executeReturningExitCode(integrations: Integrations): Int {
        return convertExceptionsToExitCode {
            CompositionPipeline.orchestrateStages(integrations)
        }
    }

    private fun convertExceptionsToExitCode(
        operation: () -> RunnerSummary
    ): Int {
        return try {
            val runnerSummary = operation()
            val exitCode =
                if (runnerSummary.errorCount > runnerSummary.maximumAllowedErrorCount) ExitCodes.VALIDATION_ERROR
                else ExitCodes.SUCCESS
            exitCode
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun handleException(e: Exception): Int {
        System.err.println("Error: ${e.message}")
        e.printStackTrace()
        return ExitCodes.GENERAL_ERROR
    }
}
