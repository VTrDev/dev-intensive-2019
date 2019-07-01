package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncommming: Boolean = false,
    date: Date = Date(),
    var text: String?
) : BaseMessage(id, from, chat, isIncommming, date) {
    override fun formatMessage(): String = "id:$id ${from?.firstName} " +
            "${if(isIncommming) "получил" else "отправил"} сообщение \"$text\" ${date.humanizeDiff()}"
}