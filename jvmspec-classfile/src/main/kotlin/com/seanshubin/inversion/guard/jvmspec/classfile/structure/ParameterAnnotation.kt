package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AnnotationStructure.Annotation
import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

data class ParameterAnnotation(
    val numAnnotations: UShort,
    val annotations: List<Annotation>
) {
    companion object {
        fun fromDataInput(
            input: DataInput,
            constantPoolMap: Map<UShort, ConstantInfo>,
            attributeInfoFromDataInput: (DataInput, Map<UShort, ConstantInfo>) -> AttributeInfo
        ): ParameterAnnotation {
            val numAnnotations = input.readUnsignedShort().toUShort()
            val annotations = (0 until numAnnotations.toInt()).map {
                Annotation.fromDataInput(input, constantPoolMap, attributeInfoFromDataInput)
            }
            return ParameterAnnotation(numAnnotations, annotations)
        }
    }
}
