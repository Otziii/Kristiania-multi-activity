package com.jorfald.moreactivities.login

import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class LoginViewModel : ViewModel() {
    fun logInUser(
        requestQueue: RequestQueue,
        username: String,
        password: String,
        callBack: (UserObject?) -> Unit
    ) {
        var url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/login"
        url += "?userName=$username&password=$password"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { jsonResponse ->
                val user = Gson().fromJson(jsonResponse, UserObject::class.java)
                callBack(user)
            },
            {
                callBack(null)
            }
        )

        requestQueue.add(stringRequest)
    }
}