package com.seanshubin.inversion.guard.appconfig

data class ApplicationRunner(
    val runner: Runnable,
    val errorCountHolder: ErrorCountHolder,
    val maximumAllowedErrorCount: Int
)
