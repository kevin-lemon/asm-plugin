package com.sample.asm
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class LogMethodVisitor (methodVisitor: MethodVisitor):MethodVisitor(Opcodes.ASM5, methodVisitor){

    override fun visitCode() {
        super.visitCode()
        mv.visitLdcInsn("TAG")
        mv.visitLdcInsn("=========onCreate=========")
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,"android/util/Log","e",
            "(Ljava/lang/String;Ljava/lang/String;)I",false)
        mv.visitInsn(Opcodes.POP)
    }

    override fun visitEnd() {
        super.visitEnd()
    }
}