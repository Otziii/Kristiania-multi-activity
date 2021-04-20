package com.jorfald.smalltalk.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jorfald.smalltalk.database.ChatDAO
import com.jorfald.smalltalk.database.ChatObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.reflect.Type


class ChatViewModel : ViewModel() {

    val isLoading = MutableLiveData(false)
    val chatMessagesLiveData = MutableLiveData<List<ChatObject>>()

    fun showLoader() {
        isLoading.postValue(true)
    }

    fun getChatMessages(
        userId: String?,
        queue: RequestQueue,
        errorCallback: () -> Unit,
    ) {
        var url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/messages"
        url += "?userId=$userId"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val listType: Type = object : TypeToken<List<ChatObject?>?>() {}.type
                val chatMessages = Gson().fromJson<List<ChatObject>>(response, listType)

                if (chatMessagesLiveData.value != chatMessages) {
                    chatMessagesLiveData.postValue(chatMessages)
                }

                isLoading.postValue(false)
            },
            {
                errorCallback()
                isLoading.postValue(false)
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun sendChatMessage(
        requestQueue: RequestQueue,
        chatObject: ChatObject,
        callback: (Boolean) -> Unit
    ) {
        val url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/sendMessage"
        val chatJson = Gson().toJson(chatObject)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(chatJson),
            {
                callback(true)
            },
            {
                callback(false)
            }
        )

        // Add the request to the RequestQueue.
        requestQueue.add(request)
    }

    fun saveChat(chatDAO: ChatDAO, newList: List<ChatObject>) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentList = chatDAO.getAllMessages()

            if (currentList.size < newList.size) {
                chatDAO.deleteAllMessages()
                chatDAO.insertChatMessages(newList)
            }
        }
    }

    fun getChatMessagesFromDatabase(chatDAO: ChatDAO) {
        CoroutineScope(Dispatchers.IO).launch {
            val messages = chatDAO.getAllMessages()
            chatMessagesLiveData.postValue(messages)

            if (messages.isEmpty()) {
                isLoading.postValue(true)
            }
        }
    }
}