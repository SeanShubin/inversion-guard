package com.seanshubin.inversion.guard.reporting

data class QualityMetrics(
    val staticInvocationsThatShouldBeInverted: Int,
    val staticInvocationsThatAreAcceptable: Int,
    val staticInvocationsThatAreIgnored: Int,
    val staticInvocationsThatShouldBeClassified: Int
)
