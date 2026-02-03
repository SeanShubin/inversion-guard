package com.seanshubin.inversion.guard.jvmspec.rules

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

//
// This file was imported from: ../jvmspec
// Module: rules
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JsonRuleLoader : RuleLoader {
    private val objectMapper: ObjectMapper = RuleMapperFactory.createObjectMapper()

    override fun load(json: String): CategoryRuleSet {
        return objectMapper.readValue(json)
    }
}
