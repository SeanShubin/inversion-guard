package com.seanshubin.inversion.guard.rules

data class CategoryRuleSet(
    val categories: Map<String, CategoryRule>,
    val core: List<String> = emptyList(),
    val boundary: List<String> = emptyList(),
    val ignore: List<String> = emptyList()
)
