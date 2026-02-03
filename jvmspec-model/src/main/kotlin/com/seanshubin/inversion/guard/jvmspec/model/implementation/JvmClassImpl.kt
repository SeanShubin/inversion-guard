package com.seanshubin.inversion.guard.jvmspec.model.implementation

import com.seanshubin.inversion.guard.jvmspec.classfile.specification.AccessFlag
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.AttributeInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.ClassFile
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.FieldInfo
import com.seanshubin.inversion.guard.jvmspec.classfile.structure.MethodInfo
import com.seanshubin.inversion.guard.jvmspec.model.api.*
import java.nio.file.Path
import java.util.*

//
// This file was imported from: ../jvmspec
// Module: model
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

class JvmClassImpl(
    private val classFile: ClassFile,
    private val methodFactory: JvmMethodFactory,
    private val fieldFactory: JvmFieldFactory,
    private val attributeFactory: JvmAttributeFactory
) : JvmClass {
    override val constants: SortedMap<UShort, JvmConstant> = classFile.constantPool.associate {
        it.index to JvmConstantFactory.createByIndex(classFile.constantPoolMap, it.index)
    }.toSortedMap()

    override val origin: Path = classFile.origin
    override val magic: Int = classFile.magic.toInt()
    override val minorVersion: Int = classFile.minorVersion.toInt()
    override val majorVersion: Int = classFile.majorVersion.toInt()
    override val thisClassName: String = lookupClassName(classFile.thisClass)
    override val superClassName: String =
        if (classFile.superClass.toInt() == 0) {
            when (thisClassName) {
                "module-info", "java/lang/Object" -> "<no super class for $thisClassName>"
                else -> throw RuntimeException("no super class for $thisClassName")
            }
        } else {
            lookupClassName(classFile.superClass)
        }

    override fun methods(): List<JvmMethod> {
        val toJvmMethod = { methodInfo: MethodInfo ->
            methodFactory.createMethod(this, methodInfo)
        }
        return classFile.methods.map(toJvmMethod)
    }

    override fun interfaces(): List<JvmConstant> {
        return classFile.interfaces.map {
            constants.getValue(it)
        }
    }

    override fun fields(): List<JvmField> {
        val toJvmField = { fieldInfo: FieldInfo ->
            fieldFactory.createField(this, fieldInfo)
        }
        return classFile.fields.map(toJvmField)
    }

    override val accessFlags: Set<AccessFlag> = classFile.accessFlags

    override fun attributes(): List<JvmAttribute> {
        val toJvmAttribute = { attributeInfo: AttributeInfo ->
            attributeFactory.createAttribute(this, attributeInfo)
        }
        return classFile.attributes.map(toJvmAttribute)
    }
}
