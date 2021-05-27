package com.sample.asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TimerClassVisitor(classVisitor:ClassVisitor):ClassVisitor(Opcodes.ASM5,classVisitor){
    private var className:String? = null
    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor =  super.visitMethod(access, name, descriptor, signature, exceptions)
        return TimerRecordMethodVisitor(className,api,methodVisitor,access,name?:"",descriptor?:"")
    }
}