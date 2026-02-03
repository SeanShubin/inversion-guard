package com.seanshubin.inversion.guard.jvmspec.rules

//
// This file was imported from: ../jvmspec
// Module: rules
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

sealed class Predicate {
    abstract fun evaluate(value: String): Boolean

    data class Contains(val value: String) : Predicate() {
        override fun evaluate(value: String): Boolean = value.contains(this.value)
    }

    data class Equals(val value: String) : Predicate() {
        override fun evaluate(value: String): Boolean = value == this.value
    }

    data class Or(val predicates: List<Predicate>) : Predicate() {
        override fun evaluate(value: String): Boolean =
            predicates.any { it.evaluate(value) }
    }
}
