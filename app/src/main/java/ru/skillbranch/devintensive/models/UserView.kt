package ru.skillbranch.devintensive.models

class UserView(
    val id: String,
    val fullname: String,
    val nickName: String,
    var avatar: String? = null,
    var status: String? = "offline",
    val initials: String?
) {
    fun printMe() {
        println("""
            id: $id 
            fullname: $fullname 
            nickName: $nickName 
            avatar: $avatar 
            status: $status 
            initials: $initials        
        """.trimIndent())
    }
}