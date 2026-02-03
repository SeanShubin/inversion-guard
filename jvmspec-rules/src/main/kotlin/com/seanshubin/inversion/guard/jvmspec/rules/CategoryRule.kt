package com.seanshubin.inversion.guard.jvmspec.rules

//
// This file was imported from: ../jvmspec
// Module: rules
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class CategoryRule(
    val methodConstraints: MethodConstraints?,
    val rules: List<RuleQuantifier>
)
