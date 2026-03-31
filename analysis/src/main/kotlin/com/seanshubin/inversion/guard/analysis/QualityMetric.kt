package com.seanshubin.inversion.guard.analysis

enum class QualityMetric {
    STATIC_INVOCATIONS_THAT_SHOULD_BE_INVERTED,
    STATIC_INVOCATIONS_THAT_ARE_ACCEPTABLE,
    STATIC_INVOCATIONS_THAT_ARE_IGNORED,
    STATIC_INVOCATIONS_THAT_SHOULD_BE_CLASSIFIED
}

fun mapToQualityMetric(invocationTypes: Set<InvocationType>): QualityMetric {
    return when {
        invocationTypes.contains(InvocationType.BOUNDARY) ->
            QualityMetric.STATIC_INVOCATIONS_THAT_SHOULD_BE_INVERTED

        invocationTypes.contains(InvocationType.CORE) ->
            QualityMetric.STATIC_INVOCATIONS_THAT_ARE_ACCEPTABLE

        invocationTypes.contains(InvocationType.IGNORE) ->
            QualityMetric.STATIC_INVOCATIONS_THAT_ARE_IGNORED

        else ->
            QualityMetric.STATIC_INVOCATIONS_THAT_SHOULD_BE_CLASSIFIED
    }
}
