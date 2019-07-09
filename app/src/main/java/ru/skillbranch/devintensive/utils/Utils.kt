package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = fullName?.split(" ")
        val firstName: String?
        val lastName: String?

        if (fullName.isNullOrBlank()) {
            firstName = null
            lastName = null
        } else {
            firstName = parts?.getOrNull(0)
            lastName = parts?.getOrNull(1)
        }

        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val data = payload.trim().replace(" ", divider)
        val sb = StringBuilder()
        data.forEach { sb.append(transliterateChar(it)) }
        return sb.toString()
    }

    private fun transliterateChar(char: Char): String {
        val isUpperCase = char.isUpperCase()
        val result = when (char.toLowerCase()) {
            'а' -> "a"
            'б' -> "b"
            'в' -> "v"
            'г' -> "g"
            'д' -> "d"
            'е' -> "e"
            'ё' -> "e"
            'ж' -> "zh"
            'з' -> "z"
            'и' -> "i"
            'й' -> "i"
            'к' -> "k"
            'л' -> "l"
            'м' -> "m"
            'н' -> "n"
            'о' -> "o"
            'п' -> "p"
            'р' -> "r"
            'с' -> "s"
            'т' -> "t"
            'у' -> "u"
            'ф' -> "f"
            'х' -> "h"
            'ц' -> "c"
            'ч' -> "ch"
            'ш' -> "sh"
            'щ' -> "sh'"
            'ъ' -> ""
            'ы' -> "i"
            'ь' -> ""
            'э' -> "e"
            'ю' -> "yu"
            'я' -> "ya"
            else -> char.toString()
        }
        return if (isUpperCase) {
            result.replaceFirst(result[0], result[0].toUpperCase())
        } else {
            result
        }
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstInitial = if (firstName.isNullOrBlank()) {
            null
        } else {
            firstName.trim()[0].toUpperCase().toString()
        }

        val lastInitial = if (lastName.isNullOrBlank()) {
            null
        } else {
            lastName.trim()[0].toUpperCase().toString()
        }

        return when {
            firstInitial == null && lastInitial == null -> null
            firstInitial == null -> lastInitial
            lastInitial == null -> firstInitial
            else -> firstInitial + lastInitial
        }
    }

}