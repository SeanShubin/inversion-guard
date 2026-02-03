package com.seanshubin.inversion.guard.jvmspec.rules

//
// This file was imported from: ../jvmspec
// Module: rules
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class Category(private val categories: Map<String, CategoryRule>) {
    private val interpreter: RuleInterpreter = RuleInterpreter(categories)

    fun fromMethodNameAndOpcodes(methodName: String, opcodeNames: List<String>): Set<String> {
        return interpreter.evaluateCategories(methodName, opcodeNames)
    }
}
