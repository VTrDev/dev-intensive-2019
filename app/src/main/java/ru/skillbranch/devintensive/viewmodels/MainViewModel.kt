package ru.skillbranch.devintensive.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {
    private val query = mutableLiveData("")
    private val chatRepository = ChatRepository
    /*private val chatItems = Transformations.map(chatRepository.loadChats()) { chats ->
        return@map chats.filter { !it.isArchived }
            .map { it.toChatItem() }
            .sortedBy { it.id.toInt() }
    }*/
    private val chatItems = Transformations.map(chatRepository.loadChats()) { chats ->
        val chatItemsResult = mutableListOf<ChatItem>()

        val archivedChats = chats.filter { it.isArchived }
        if (archivedChats.isNotEmpty()) {
            //Log.d("M_MainViewModel", "${archivedChats.count()}")
            val totalMessageCount = archivedChats.sumBy { it.messages.count() }
            chatItemsResult.add(
                archivedChats
                    .sortedByDescending { it.lastMessageDate() }
                    .map { it.toChatItem() } [0]
                    .copy(
                        messageCount = totalMessageCount,
                        chatType = ChatType.ARCHIVE
                    )
            )
            //Log.d("M_MainViewModel", "$chatItemsResult")
        }

        chatItemsResult.addAll(
            chats.filter { !it.isArchived }
                .map { it.toChatItem() }
                .sortedBy { it.id.toInt() }
        )

        return@map chatItemsResult
    }

    fun getChatData(): LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val chats = chatItems.value!!

            result.value = if (queryStr.isEmpty()) chats
            else chats.filter { it.title.contains(queryStr, true) && it.chatType != ChatType.ARCHIVE }
        }

        result.addSource(chatItems) { filterF.invoke() }
        result.addSource(query) { filterF.invoke() }

        return result
    }

    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String?) {
        query.value = text
    }
}