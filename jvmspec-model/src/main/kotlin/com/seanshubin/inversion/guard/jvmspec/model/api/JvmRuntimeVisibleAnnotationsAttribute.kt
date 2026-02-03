package com.seanshubin.inversion.guard.jvmspec.model.api

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AnnotationStructure.Annotation

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

interface JvmRuntimeVisibleAnnotationsAttribute : JvmAttribute {
    val numAnnotations: UShort
    val annotations: List<Annotation>
}
