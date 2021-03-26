package com.jorfald.moreactivities.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.jorfald.moreactivities.UserManager
import com.jorfald.moreactivities.database.UserDAO
import com.jorfald.moreactivities.database.UserObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val userLiveData = MutableLiveData<UserObject>()
    val loginError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun logInUser(
        requestQueue: RequestQueue,
        username: String,
        password: String
    ) {
        isLoading.postValue(true)

        var url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/login"
        url += "?userName=$username&password=$password"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { jsonResponse ->
                val user = Gson().fromJson(jsonResponse, UserObject::class.java)
                userLiveData.postValue(user)
            },
            {
                loginError.postValue(true)
                isLoading.postValue(false)
            }
        )

        requestQueue.add(stringRequest)
    }

    fun saveUser(userDao: UserDAO, user: UserObject, doneCallback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(user)
            UserManager.loggedInUser = user
            doneCallback()
        }
    }
}