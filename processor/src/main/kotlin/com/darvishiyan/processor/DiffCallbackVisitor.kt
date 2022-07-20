package com.darvishiyan.processor

import com.darvishiyan.annotations.DiffCallbackID
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.*
import java.io.OutputStream

class DiffCallbackVisitor(
    private val file: OutputStream,
    private val logger: KSPLogger,
    private val options: Map<String, String>,
) : KSVisitorVoid() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        if (classDeclaration.classKind != ClassKind.CLASS) {
            logger.error("Only interface can be annotated with @DiffCallback", classDeclaration)
            return
        }

        file += classDeclaration.getAllProperties().mapNotNull { visitPropertyDeclaration(it) }.joinToString(" && ")
    }

    private fun visitPropertyDeclaration(propertyDeclaration: KSPropertyDeclaration): String? {
        return propertyDeclaration.annotations.getAnnotationIfExist(DiffCallbackID::class.java.simpleName)?.let {
            "oldItem.${propertyDeclaration.simpleName.asString()} == newItem.${propertyDeclaration.simpleName.asString()}"
        }
    }
}
