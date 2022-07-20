package com.darvishiyan.processor

import com.darvishiyan.annotations.DiffCallback
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import java.io.OutputStream

class DiffCallbackSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation(DiffCallback::class.java.name)
            .filterIsInstance<KSClassDeclaration>()


        if (symbols.iterator().hasNext().not()) return emptyList()

        symbols.forEach { symbol ->

            val packageName = symbol.packageName.asString()
            val className = symbol.simpleName.asString()
            val fileName = "${className}DiffCallback"

            val comment = symbol.annotations.getAnnotation(DiffCallback::class.java.simpleName).arguments.getParameterValue<String>(DiffCallback.comment)

            val file: OutputStream = codeGenerator.createNewFile(
                dependencies = Dependencies(false),
                packageName = packageName,
                fileName = fileName
            )

            file += "package $packageName\n\n"

            file += "import androidx.recyclerview.widget.DiffUtil\n\n"

            if (Options.isActive(options, Options.writeComment)) {
                file += "// $comment\n"
                Options.getText(options, Options.defaultComment)?.takeIf { it.isNotEmpty() }?.let { defaultComment ->
                    file += "// $defaultComment\n"
                }
            }

            file += "object $fileName : DiffUtil.ItemCallback<$className>() {\n"

            file += "\toverride fun areItemsTheSame(oldItem: $className, newItem: $className): Boolean {\n"
            file += "\t\treturn "

            symbol.accept(DiffCallbackVisitor(file, logger, options), Unit)

            file += " \n"

            file += "\t}\n\n"

            file += "\toverride fun areContentsTheSame(oldItem: $className, newItem: $className): Boolean {\n"
            file += "\t\treturn oldItem == newItem\n"
            file += "\t}\n"

            file += "}"

            file.close()
        }

        return symbols.filterNot { it.validate() }.toList()
    }
}