package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName?.trim() == "") return null to null

        val parts: List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
//        return Pair(firstName, lastName)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String { // HW 1:18
        return "Makeev_Mihail"
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrBlank()) {
            if (lastName.isNullOrBlank()) {
                return null
            }
            return lastName[0].toString().toUpperCase()
        } else {
            if (!lastName.isNullOrBlank()) {
                return firstName[0].toString().toUpperCase() + lastName[0].toString().toUpperCase()
            }
            return firstName[0].toString().toUpperCase()
        }
    }
}