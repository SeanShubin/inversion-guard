package com.seanshubin.inversion.guard.jvmspec.analysis.statistics

import com.seanshubin.inversion.guard.jvmspec.analysis.filtering.MatchedFilterEvent
import com.seanshubin.inversion.guard.jvmspec.analysis.filtering.UnmatchedFilterEvent

//
// This file was imported from: ../jvmspec
// Module: analysis
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface Stats {
    val matchedFilterEvents: List<MatchedFilterEvent>
    val unmatchedFilterEvents: List<UnmatchedFilterEvent>
    val registeredPatterns: Map<String, Map<String, List<String>>> // category -> type -> patterns
    val registeredLocalPatterns: Map<String, Map<String, List<String>>> // category -> type -> local patterns
    fun consumeMatchedFilterEvent(event: MatchedFilterEvent)
    fun consumeUnmatchedFilterEvent(event: UnmatchedFilterEvent)
    fun registerPatterns(category: String, patternsByType: Map<String, List<String>>)
    fun registerLocalPatterns(category: String, patternsByType: Map<String, List<String>>)
}
