package com.seanshubin.inversion.guard.rules

interface RuleLoader {
    fun load(json: String): CategoryRuleSet
}
