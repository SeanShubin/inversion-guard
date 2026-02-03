package com.seanshubin.inversion.guard.jvmspec.analysis.matching

//
// This file was imported from: ../jvmspec
// Module: analysis
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class StringListRuleMatcher(val list: List<String>, var index: Int = 0, var rulesMatched: Boolean = true) {
    fun expectZeroOrMore(rule: (String) -> Boolean) {
        if (!rulesMatched) return
        while (!atEnd() && rule(current())) {
            index++
        }
    }

    fun expectExactly(quantity: Int, rule: (String) -> Boolean) {
        if (!rulesMatched) return
        if (quantity != 0 && atEnd()) rulesMatched = false
        val targetIndex = index + quantity
        while (rulesMatched && !atEnd() && index < targetIndex) {
            if (!rule(current())) {
                rulesMatched = false
            }
            index++
        }
    }

    fun expectAtEnd() {
        if (!atEnd()) rulesMatched = false
    }

    private fun atEnd() = index == list.size
    private fun current(): String = list[index]
}
