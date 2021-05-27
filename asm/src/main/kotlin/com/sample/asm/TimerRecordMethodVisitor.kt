package com.sample.asm

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Created by wxk on 2021/5/27.
 */
class TimerRecordMethodVisitor(api:Int,val methodVisitor:MethodVisitor,access:Int,name:String,descriptor:String)
    :AdviceAdapter(api,methodVisitor,access, name, descriptor) {

    override fun onMethodEnter() {
        val label0 = Label()
        methodVisitor.visitLabel(label0)
        methodVisitor.visitLineNumber(12, label0)
        methodVisitor.visitMethodInsn(
            INVOKESTATIC,
            "java/lang/System",
            "currentTimeMillis",
            "()J",
            false
        )
        methodVisitor.visitVarInsn(LSTORE, 2)
        super.onMethodEnter()
    }

    override fun onMethodExit(opcode: Int) {
        val label3 = Label()
        methodVisitor.visitLabel(label3)
        methodVisitor.visitLineNumber(15, label3)
        methodVisitor.visitMethodInsn(
            INVOKESTATIC,
            "java/lang/System",
            "currentTimeMillis",
            "()J",
            false
        )
        methodVisitor.visitVarInsn(LSTORE, 4)
        val label4 = Label()
        methodVisitor.visitLabel(label4)
        methodVisitor.visitLineNumber(16, label4)
        methodVisitor.visitLdcInsn("$name finish")
        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder")
        methodVisitor.visitInsn(DUP)
        methodVisitor.visitMethodInsn(
            INVOKESPECIAL,
            "java/lang/StringBuilder",
            "<init>",
            "()V",
            false
        )
        methodVisitor.visitVarInsn(LLOAD, 4)
        methodVisitor.visitVarInsn(LLOAD, 2)
        methodVisitor.visitInsn(LSUB)
        methodVisitor.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "append",
            "(J)Ljava/lang/StringBuilder;",
            false
        )
        methodVisitor.visitLdcInsn("ms")
        methodVisitor.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "append",
            "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
            false
        )
        methodVisitor.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "toString",
            "()Ljava/lang/String;",
            false
        )
        methodVisitor.visitMethodInsn(
            INVOKESTATIC,
            "android/util/Log",
            "d",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            false
        )
        methodVisitor.visitInsn(POP)
        super.onMethodExit(opcode)
    }
}