package com.seanshubin.inversion.guard.jvmspec.infrastructure.collections

//
// This file was imported from: ../jvmspec
// Module: infrastructure
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class Tree(
    val node: String,
    val children: List<Tree> = emptyList()
) {
    fun toLines(indent: (String) -> String): List<String> =
        listOf(node) + children.flatMap { it.toLines(indent).map(indent) }
}
