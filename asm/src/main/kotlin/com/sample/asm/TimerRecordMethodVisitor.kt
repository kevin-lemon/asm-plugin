package com.sample.asm

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Created by wxk on 2021/5/27.
 */
class TimerRecordMethodVisitor(
    private val className: String?,
    api: Int,
    private val methodVisitor: MethodVisitor,
    access: Int,
    name: String,
    descriptor: String
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    override fun onMethodEnter() {
        val label0 = Label()
        visitLabel(label0)
        visitLineNumber(12, label0)
        visitMethodInsn(
            INVOKESTATIC,
            "java/lang/System",
            "currentTimeMillis",
            "()J",
            false
        )
        visitVarInsn(LSTORE, 2)
        super.onMethodEnter()
    }

    override fun onMethodExit(opcode: Int) {
        run {
            val label3 = Label()
            visitLabel(label3)
            visitLineNumber(15, label3)
            visitMethodInsn(
                INVOKESTATIC,
                "java/lang/System",
                "currentTimeMillis",
                "()J",
                false
            )

            visitVarInsn(LSTORE, 4)
            val label4 = Label()
            visitLabel(label4)
            visitLineNumber(16, label4)
            visitLdcInsn("$className -> $name finish")
            visitTypeInsn(NEW, "java/lang/StringBuilder")
            visitInsn(DUP)
            visitMethodInsn(
                INVOKESPECIAL,
                "java/lang/StringBuilder",
                "<init>",
                "()V",
                false
            )
            visitVarInsn(LLOAD, 4)
            visitVarInsn(LLOAD, 2)
            visitInsn(LSUB)
            visitMethodInsn(
                INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "append",
                "(J)Ljava/lang/StringBuilder;",
                false
            )
            visitLdcInsn("ms")
            visitMethodInsn(
                INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                false
            )
            visitMethodInsn(
                INVOKEVIRTUAL,
                "java/lang/StringBuilder",
                "toString",
                "()Ljava/lang/String;",
                false
            )
            visitMethodInsn(
                INVOKESTATIC,
                "android/util/Log",
                "d",
                "(Ljava/lang/String;Ljava/lang/String;)I",
                false
            )
            visitInsn(POP)
        }
        super.onMethodExit(opcode)
    }
}