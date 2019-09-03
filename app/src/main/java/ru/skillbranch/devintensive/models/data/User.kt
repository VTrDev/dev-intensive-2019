package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User (
        val id: String,
        var firstName: String?,
        var lastName: String?,
        var avatar: String?,
        var rating: Int = 0,
        var respect: Int = 0,
        val lastVisit: Date? = null,
        val isOnline: Boolean = false
) {
    fun toUserItems(): UserItem {
        val lastActivity = when {
            lastVisit == null -> "Еще не разу не заходил"
            isOnline -> "online"
            else -> "Последний раз был ${lastVisit.humanizeDiff()} "
        }

        return UserItem(
            id,
            "${firstName.orEmpty()} ${lastName.orEmpty()}",
            Utils.toInitials(firstName, lastName),
            avatar,
            lastActivity,
            false,
            isOnline
        )
    }

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this(id, "John", "Doe")

    private constructor(builder: Builder) : this(
        builder.id,
        builder.firstName,
        builder.lastName,
        builder.avatar,
        builder.rating,
        builder.respect,
        builder.lastVisit,
        builder.isOnline
    )

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullName: String?): User {
            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(
                id = "$lastId",
                firstName = firstName,
                lastName = lastName
            )
        }
    }

    class Builder { // https://stackoverflow.com/questions/36140791/how-to-implement-builder-pattern-in-kotlin
        var id: String = "-1"
            private set

        var firstName: String? = null
            private set

        var lastName: String? = null
            private set

        var avatar: String? = null
            private set

        var rating: Int = 0
            private set

        var respect: Int = 0
            private set

        var lastVisit: Date? = null
            private set

        var isOnline: Boolean = false
            private set

        fun id(id: String) = apply { this.id = id }

        fun firstName(firstName: String) = apply { this.firstName = firstName }

        fun lastName(lastName: String) = apply { this.lastName = lastName }

        fun avatar(avatar: String) = apply { this.avatar = avatar }

        fun rating(rating: Int) = apply { this.rating = rating }

        fun respect(respect: Int) = apply { this.respect = respect }

        fun lastVisit(lastVisit: Date) = apply { this.lastVisit = lastVisit }

        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }

        fun build() = User(this)
    }

    /*private fun getIntro() = """
        tu tu ru tuuuuu !!!
        tu tu ru tuuuuuuuuuu ...

        tu tu ru tuuuuu !!!
        tu tu ru tuuuuuuuuuu ...
        ${"\n\n\n"}
        $firstName $lastName

    """.trimIndent()

    fun printMe() = println("""
            id: $id
            firstName: $firstName
            lastName: $lastName
            avatar: $avatar
            rating: $rating
            respect: $respect
            lastVisit: $lastVisit
            isOnline: $isOnline
        """.trimIndent())*/
}