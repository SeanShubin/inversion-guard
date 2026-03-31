package com.seanshubin.inversion.guard.analysis

import java.nio.file.Path

data class ClassAnalysisSummary(
    val className: String,
    val origin: Path,
    val methodAnalysisList: List<MethodAnalysis>
) {
    fun complexity(): Int {
        return methodAnalysisList.sumOf { it.complexity }
    }

    fun countProblems(): Int {
        return methodAnalysisList
            .filter { !it.isBoundaryLogic() }
            .sumOf { method ->
                method.staticInvocations.count { invocation ->
                    val metric = mapToQualityMetric(invocation.invocationTypes)
                    metric == QualityMetric.STATIC_INVOCATIONS_THAT_SHOULD_BE_INVERTED ||
                            metric == QualityMetric.STATIC_INVOCATIONS_THAT_SHOULD_BE_CLASSIFIED
                }
            }
    }

    private fun countNonBoundaryLogic(): Int {
        return methodAnalysisList.count { !it.isBoundaryLogic() }
    }

    companion object {
        fun fromClassAnalysis(analysis: ClassAnalysis): ClassAnalysisSummary {
            return ClassAnalysisSummary(
                className = analysis.jvmClass.thisClassName,
                origin = analysis.jvmClass.origin,
                methodAnalysisList = analysis.methodAnalysisList
            )
        }
    }
}
