package com.jorfald.moreactivities.chat

import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type


class MainViewModel : ViewModel() {
    fun getChatMessages(
        queue: RequestQueue,
        successCallback: (List<ChatObject>) -> Unit,
        errorCallback: () -> Unit,
    ) {
        val url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/messages"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val listType: Type = object : TypeToken<List<ChatObject?>?>() {}.type
                val chatMessages = Gson().fromJson<List<ChatObject>>(response, listType)

                successCallback(chatMessages)
            },
            {
                errorCallback()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun sendChatMessage(requestQueue: RequestQueue, chatObject: ChatObject, callback: (Boolean) -> Unit) {
        val url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/sendMessage"
        val chatJson = Gson().toJson(chatObject)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(chatJson),
            { jsonObjectResponse ->
                callback(true)
            },
            { error ->
                callback(false)
            }
        )

        // Add the request to the RequestQueue.
        requestQueue.add(request)
    }
}