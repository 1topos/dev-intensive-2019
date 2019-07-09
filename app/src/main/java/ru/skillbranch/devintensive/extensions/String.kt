package ru.skillbranch.devintensive.extensions

fun String.truncate(count : Int = 16) : String {
    val sb = StringBuilder(this.trim())
    if (sb.count() > count) {
        sb.delete(count, sb.count())
        if (sb.last() == ' ')
            sb.deleteCharAt(sb.count() - 1)
        sb.append("...")
    }
    return sb.toString()
}

fun String.stripHtml(): String? =
    this
        .replace(Regex("(&[a-z]*?;)|(&#[0-9]*;)"), "")
        .replace(Regex("<[^>]*>"), "")
        .replace(Regex("[ ]+"), " ")