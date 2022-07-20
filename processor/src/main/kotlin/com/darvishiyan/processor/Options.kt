package com.darvishiyan.processor

class Options {
    companion object {
        const val writeComment = "writeComment"
        const val defaultComment = "defaultComment"

        @Suppress("UNCHECKED_CAST")
        fun <T> getValue(options: Map<String, String>, option: String): T? = options[option] as? T
        fun isActive(options: Map<String, String>, option: String) = options[option] == "true"
        fun getText(options: Map<String, String>, option: String) = options[option]
    }
}