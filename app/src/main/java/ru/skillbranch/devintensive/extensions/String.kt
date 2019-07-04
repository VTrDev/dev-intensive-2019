package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    if (length <= 0) return ""

    val str = this.trimEnd()

    return if (str.length <= length) str
        else this.subSequence(0, length).toString().trimEnd().plus("...")
}