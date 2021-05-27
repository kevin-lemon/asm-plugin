package com.sample.asm

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.FileOutputStream

class TimerTransform :Transform(),Plugin<Project>{

    override fun getName(): String {
        return "TimerTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun apply(p0: Project) {
        p0.extensions.create<TimerExtension>("timerprovider",TimerExtension::class.java)
        var extension = p0.extensions.getByType(TimerExtension::class.java)
        print("wxk"+extension.enableJar)
        val appExtension = p0.extensions.getByType(AppExtension::class.java)
        appExtension.registerTransform(this)
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        transformInvocation?.run {
            if (!isIncremental){
                outputProvider.deleteAll()
            }
            inputs.forEach {transformInput->
                transformInput.directoryInputs.forEach {directoryInput->
                    if (directoryInput.file.isDirectory){
                        FileUtils.getAllFiles(directoryInput.file).forEach {file ->
                            val name = file.name
                            if (name.endsWith(".class") && name != ("R.class")
                                && !name.startsWith("R\$") && name != ("BuildConfig.class")){
                                val classPath = file.absolutePath
                                val cr = ClassReader(file.readBytes())
                                val cw = ClassWriter(cr,ClassWriter.COMPUTE_MAXS)
                                val visitor = TimerClassVisitor(cw)
                                cr.accept(visitor,ClassReader.EXPAND_FRAMES)
                                val bytes = cw.toByteArray()
                                val fos = FileOutputStream(classPath)
                                fos.write(bytes)
                                fos.close()
                            }
                        }
                    }
                    val dest = outputProvider.getContentLocation(
                        directoryInput.name,directoryInput.contentTypes,directoryInput.scopes,Format.DIRECTORY
                    )
                    FileUtils.copyDirectoryToDirectory(directoryInput.file,dest)
                }
                //androidX 不处理jar会报错找不到类
                transformInput.jarInputs.forEach {
                    val dest = outputProvider?.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.JAR
                    )
                    FileUtils.copyFile(it.file, dest)
                }
            }
        }

    }

}