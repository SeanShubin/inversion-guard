package com.seanshubin.inversion.guard.jvmspec.classfile.structure

import com.seanshubin.inversion.guard.jvmspec.classfile.specification.ConstantPoolTag
import java.io.DataInput

//
// This file was imported from: ../jvmspec
// Module: classfile
//
// Before editing this file, consider whether updating the source project
// and re-importing would be a better approach.
//

object ConstantInfoFactory {
    fun fromDataInput(index: UShort, input: DataInput): ConstantInfo {
        val tagByte = input.readUnsignedByte().toUByte()
        val tag = ConstantPoolTag.fromId(tagByte)
        val factory = factoryMap[tag]
            ?: throw IllegalArgumentException("Unknown constant tag: $tag")
        return factory(tag, index, input)
    }

    val factoryMap: Map<ConstantPoolTag, (ConstantPoolTag, UShort, DataInput) -> ConstantInfo> = mapOf(
        ConstantUtf8Info.TAG to ConstantUtf8Info::fromDataInput,
        ConstantIntegerInfo.TAG to ConstantIntegerInfo::fromDataInput,
        ConstantFloatInfo.TAG to ConstantFloatInfo::fromDataInput,
        ConstantLongInfo.TAG to ConstantLongInfo::fromDataInput,
        ConstantDoubleInfo.TAG to ConstantDoubleInfo::fromDataInput,
        ConstantClassInfo.TAG to ConstantClassInfo::fromDataInput,
        ConstantStringInfo.TAG to ConstantStringInfo::fromDataInput,
        ConstantFieldRefInfo.TAG to ConstantFieldRefInfo::fromDataInput,
        ConstantMethodRefInfo.TAG to ConstantMethodRefInfo::fromDataInput,
        ConstantInterfaceMethodRefInfo.TAG to ConstantInterfaceMethodRefInfo::fromDataInput,
        ConstantNameAndTypeInfo.TAG to ConstantNameAndTypeInfo::fromDataInput,
        ConstantMethodHandleInfo.TAG to ConstantMethodHandleInfo::fromDataInput,
        ConstantMethodTypeInfo.TAG to ConstantMethodTypeInfo::fromDataInput,
        ConstantDynamicInfo.TAG to ConstantDynamicInfo::fromDataInput,
        ConstantInvokeDynamicInfo.TAG to ConstantInvokeDynamicInfo::fromDataInput,
        ConstantModuleInfo.TAG to ConstantModuleInfo::fromDataInput,
        ConstantPackageInfo.TAG to ConstantPackageInfo::fromDataInput
    )
}
