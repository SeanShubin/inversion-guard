package com.seanshubin.inversion.guard.reporting

import com.seanshubin.inversion.guard.analysis.ClassAnalysisSummary
import com.seanshubin.inversion.guard.analysis.MethodAnalysis

interface QualityMetricsDetailReportGenerator {
    fun generate(analysisList: List<ClassAnalysisSummary>): QualityMetricsDetailReport
}

class QualityMetricsDetailReportGeneratorImpl : QualityMetricsDetailReportGenerator {
    override fun generate(analysisList: List<ClassAnalysisSummary>): QualityMetricsDetailReport {
        val classDetails = analysisList
            .filter { it.countProblems() > 0 }
            .map { classAnalysis ->
                val methodDetails = classAnalysis.methodAnalysisList
                    .filter { !it.isBoundaryLogic() && it.staticInvocations.isNotEmpty() }
                    .map { methodAnalysis ->
                        val invocationDetails = methodAnalysis.staticInvocations
                            .map { invocation ->
                                val invocationTypeName = if (invocation.invocationTypes.isEmpty()) {
                                    "UNCLASSIFIED"
                                } else if (invocation.invocationTypes.size == 1) {
                                    invocation.invocationTypes.first().name
                                } else {
                                    "[${invocation.invocationTypes.joinToString(", ") { it.name }}]"
                                }
                                QualityMetricsInvocationDetail(
                                    invocationType = invocationTypeName,
                                    opcode = invocation.invocationOpcodeName,
                                    className = invocation.className,
                                    methodName = invocation.methodName,
                                    signature = invocation.signature.compactFormat()
                                )
                            }

                        QualityMetricsMethodDetail(
                            methodSignature = formatMethodSignature(methodAnalysis),
                            complexity = methodAnalysis.complexity,
                            isBoundaryLogic = methodAnalysis.isBoundaryLogic(),
                            boundaryLogicCategories = methodAnalysis.boundaryLogicCategories.sorted(),
                            invocations = invocationDetails
                        )
                    }
                    .sortedWith(
                        compareByDescending<QualityMetricsMethodDetail> { it.invocations.size }
                            .thenBy { it.methodSignature }
                    )

                QualityMetricsClassDetail(
                    className = classAnalysis.className,
                    problemCount = classAnalysis.countProblems(),
                    complexity = classAnalysis.complexity(),
                    methods = methodDetails
                )
            }
            .sortedWith(
                compareByDescending<QualityMetricsClassDetail> { it.problemCount }
                    .thenBy { it.className }
            )

        return QualityMetricsDetailReport(classes = classDetails)
    }

    private fun formatMethodSignature(methodAnalysis: MethodAnalysis): String {
        return methodAnalysis.signature.javaFormat()
    }
}
