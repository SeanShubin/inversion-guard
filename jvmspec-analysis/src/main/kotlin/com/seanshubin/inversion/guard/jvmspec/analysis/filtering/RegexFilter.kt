package com.seanshubin.inversion.guard.jvmspec.analysis.filtering

//
// This file was imported from: ../jvmspec
// Module: analysis
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class RegexFilter(
    private val category: String,
    private val patternsByType: Map<String, List<String>>,
    private val matchedFilterEvent: (MatchedFilterEvent) -> Unit,
    private val unmatchedFilterEvent: (UnmatchedFilterEvent) -> Unit,
    private val registerPatterns: ((String, Map<String, List<String>>) -> Unit)? = null
) : Filter {
    private val regexesByType: Map<String, List<Regex>> =
        patternsByType.mapValues { (_, patterns) ->
            patterns.map { Regex(it) }
        }

    init {
        registerPatterns?.invoke(category, patternsByType)
    }

    override fun match(text: String): Set<String> {
        val matchedTypes = mutableSetOf<String>()
        var hasMatch = false

        regexesByType.forEach { (type, regexList) ->
            regexList.forEach { regex ->
                if (regex.matches(text)) {
                    matchedFilterEvent(MatchedFilterEvent(category, type, regex.pattern, text))
                    matchedTypes.add(type)
                    hasMatch = true
                }
            }
        }

        if (!hasMatch) {
            unmatchedFilterEvent(UnmatchedFilterEvent(category, text))
        }

        return matchedTypes
    }
}
