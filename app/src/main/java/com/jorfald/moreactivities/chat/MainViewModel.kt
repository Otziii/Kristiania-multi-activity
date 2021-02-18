package com.jorfald.moreactivities.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jorfald.moreactivities.chat.ChatObject
import java.lang.reflect.Type


class MainViewModel : ViewModel() {
    fun getChatMessages(
        context: Context,
        successCallback: (List<ChatObject>) -> Unit,
        errorCallback: () -> Unit,
    ) {
        val gson = Gson()

        val queue = Volley.newRequestQueue(context)
        val url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/messages"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val listType: Type = object : TypeToken<List<ChatObject?>?>() {}.type
                val chatMessages = gson.fromJson<List<ChatObject>>(response, listType)

                successCallback(chatMessages)
            },
            {
                errorCallback()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}