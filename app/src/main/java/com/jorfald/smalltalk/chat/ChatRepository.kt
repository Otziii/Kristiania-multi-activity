package com.jorfald.smalltalk.chat

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jorfald.smalltalk.BASE_URL
import com.jorfald.smalltalk.SmallTalkApplication
import com.jorfald.smalltalk.database.AppDatabase
import com.jorfald.smalltalk.database.ChatObject
import org.json.JSONObject
import java.lang.reflect.Type

class ChatRepository {

    private val chatDAO =
        AppDatabase.getDatabase(SmallTalkApplication.application.applicationContext).chatDAO()
    private val requestQueue =
        Volley.newRequestQueue(SmallTalkApplication.application.applicationContext)

    fun fetchChatMessages(userId: String, callback: (List<ChatObject>?) -> Unit) {
        val url = "${BASE_URL}/messages?userId=$userId"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val listType: Type = object : TypeToken<List<ChatObject?>?>() {}.type
                val chatMessages = Gson().fromJson<List<ChatObject>>(response, listType)

                callback(chatMessages)
            },
            {
                callback(null)
            }
        )

        requestQueue.add(stringRequest)
    }

    fun sendMessage(chatRequestsObject: ChatObject, callback: (Boolean) -> Unit) {
        val url = "${BASE_URL}/sendMessage"
        val chatRequestJson = Gson().toJson(chatRequestsObject)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(chatRequestJson),
            {
                callback(true)
            },
            {
                callback(false)
            }
        )

        requestQueue.add(request)
    }

    fun getChatMessagesFromDB(): List<ChatObject> {
        return chatDAO.getAllMessages()
    }

    fun saveChatMessagesToDB(newList: List<ChatObject>) {
        val currentList = getChatMessagesFromDB()

        if (newList.size != currentList.size) {
            chatDAO.deleteAllMessages()
            chatDAO.insertChatMessages(newList)
        }
    }
}
