package com.jorfald.smalltalk.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jorfald.smalltalk.database.ChatObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: add test coverage
open class ChatViewModel : ViewModel() {

    var chatRepository = ChatRepository()

    val isLoading = MutableLiveData(false)
    val chatMessagesLiveData = MutableLiveData<List<ChatObject>>()

    fun getChatMessages(
        userId: String,
        errorCallback: () -> Unit
    ) {
        chatRepository.fetchChatMessages(userId) { chatMessagesList ->
            if (chatMessagesList != null) {
                if (chatMessagesLiveData.value != chatMessagesList) {
                    chatMessagesLiveData.postValue(chatMessagesList)
                }
            } else {
                errorCallback()
            }

            isLoading.postValue(false)
        }
    }

    open fun sendChatMessage(
        chatObject: ChatObject,
        callback: ((Boolean) -> Unit)
    ) {
        chatRepository.sendMessage(chatObject, callback)
    }

    fun saveChat(newList: List<ChatObject>) {
        CoroutineScope(Dispatchers.IO).launch {
            chatRepository.saveChatMessagesToDB(newList)
        }
    }

    fun getChatMessagesFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val messages = chatRepository.getChatMessagesFromDB()

            chatMessagesLiveData.postValue(messages)

            if (messages.isEmpty()) {
                isLoading.postValue(true)
            }
        }
    }
}