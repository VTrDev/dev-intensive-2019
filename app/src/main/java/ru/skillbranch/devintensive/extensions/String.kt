package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    val str = this.trimEnd()

    return if (str.length <= length) str
        else this.subSequence(0, length).toString().trimEnd().plus("...")
}