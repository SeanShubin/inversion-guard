package com.seanshubin.inversion.guard.rules

data class CategoryRule(
    val methodConstraints: MethodConstraints?,
    val rules: List<RuleQuantifier>
)
