package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    if (length <= 0) return ""

    val str = this.trimEnd()

    return if (str.length <= length) str
        else this.subSequence(0, length).toString().trimEnd().plus("...")
}

fun String.stripHtml(): String {
    return this
        .replace("<(.|\\n)*?>".toRegex(), "")
        .replace("&.{2,};".toRegex(), "")
        .replace("\\s+".toRegex(), " ")
}

fun String.isGitHubRepoUrl(): Boolean {
    val matchResult = "^(https?://)?(www.)?github.com/([A-Za-z0-9-_]{3,})$"
        .toRegex().find(this) ?: return false
    return !listOf("enterprise", "features", "topics", "collections",
        "trending", "events", "marketplace", "pricing", "nonprofit",
        "customer-stories", "security", "login", "join").contains(matchResult.groupValues[3])
}