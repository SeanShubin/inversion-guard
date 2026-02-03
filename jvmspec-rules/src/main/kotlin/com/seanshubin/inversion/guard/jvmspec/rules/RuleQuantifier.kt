package com.seanshubin.inversion.guard.jvmspec.rules

//
// This file was imported from: ../jvmspec
// Module: rules
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

sealed class RuleQuantifier {
    data class ZeroOrMore(val predicate: Predicate) : RuleQuantifier()
    data class Exactly(val count: Int, val predicate: Predicate) : RuleQuantifier()
    object AtEnd : RuleQuantifier()
}
