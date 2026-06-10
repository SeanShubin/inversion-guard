package com.seanshubin.inversion.guard.reporting

data class QualityMetricsDetailReport(
    val classes: List<QualityMetricsClassDetail>,
    val boundaryLogicClasses: List<BoundaryLogicClassDetail>
)

data class BoundaryLogicClassDetail(
    val className: String,
    val methods: List<BoundaryLogicMethodDetail>
)

data class BoundaryLogicMethodDetail(
    val methodSignature: String,
    val boundaryLogicCategories: List<String>
)

data class QualityMetricsClassDetail(
    val className: String,
    val problemCount: Int,
    val complexity: Int,
    val methods: List<QualityMetricsMethodDetail>
)

data class QualityMetricsMethodDetail(
    val methodSignature: String,
    val complexity: Int,
    val isBoundaryLogic: Boolean,
    val boundaryLogicCategories: List<String>,
    val invocations: List<QualityMetricsInvocationDetail>
)

data class QualityMetricsInvocationDetail(
    val invocationType: String,
    val opcode: String,
    val className: String,
    val methodName: String,
    val signature: String
)
