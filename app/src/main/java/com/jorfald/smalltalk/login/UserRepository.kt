package com.jorfald.smalltalk.login

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.jorfald.smalltalk.BASE_URL
import com.jorfald.smalltalk.SmallTalkApplication
import com.jorfald.smalltalk.UserManager
import com.jorfald.smalltalk.database.AppDatabase
import com.jorfald.smalltalk.database.UserObject

class UserRepository {

    private val userDao =
        AppDatabase.getDatabase(SmallTalkApplication.application.applicationContext).userDAO()

    fun logInUser(username: String, password: String, callback: (UserObject?) -> Unit) {
        val url = "${BASE_URL}/login?userName=$username&password=$password"
        val requestQueue =
            Volley.newRequestQueue(SmallTalkApplication.application.applicationContext)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { jsonResponse ->
                val user = Gson().fromJson(jsonResponse, UserObject::class.java)
                callback(user)
            },
            {
                //TODO: Log error
                callback(null)
            }
        )

        requestQueue.add(stringRequest)
    }

    fun saveUser(user: UserObject) {
        userDao.insertUser(user)

        UserManager.loggedInUser = user
    }

    fun getUser(): UserObject? {
        var user: UserObject? = null

        try {
            user = userDao.getUser()
        } catch (e: Exception) {
            //TODO: Log exception
        }

        if (user != null) {
            UserManager.loggedInUser = user
        }

        return user
    }
}