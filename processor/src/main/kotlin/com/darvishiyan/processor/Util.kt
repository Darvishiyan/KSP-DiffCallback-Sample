package com.darvishiyan.processor

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.symbol.Modifier
import java.io.OutputStream

operator fun OutputStream.plusAssign(str: String) = write(str.toByteArray())

fun Sequence<KSAnnotation>.getAnnotation(target: String): KSAnnotation {
    return getAnnotationIfExist(target) ?:
    throw NoSuchElementException("Sequence contains no element matching the predicate.")
}

fun Sequence<KSAnnotation>.getAnnotationIfExist(target: String): KSAnnotation? {
    for (element in this) if (element.shortName.asString() == target) return element
    return null
}

@Suppress("UNCHECKED_CAST")
fun <T> List<KSValueArgument>.getParameterValue(target: String): T {
    return getParameterValueIfExist(target) ?:
    throw NoSuchElementException("Sequence contains no element matching the predicate.")
}

@Suppress("UNCHECKED_CAST")
fun <T> List<KSValueArgument>.getParameterValueIfExist(target: String): T {
    for (element in this) if (element.name?.asString() == target) (element.value as? T)?.let { return it }
    throw NoSuchElementException("Sequence contains no element matching the predicate.")
}

fun Set<Modifier>.findByNameIgnoreCase(name: String): Modifier? {
    return find { it.name.equals(name, true) }
}