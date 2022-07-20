package com.darvishiyan.annotations

@Target(AnnotationTarget.CLASS)
annotation class DiffCallback(val comment: String) {
    companion object {
        const val comment = "comment"
    }
}
