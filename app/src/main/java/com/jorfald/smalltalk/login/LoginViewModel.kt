package com.jorfald.smalltalk.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.jorfald.smalltalk.UserManager
import com.jorfald.smalltalk.chat.DrinkList
import com.jorfald.smalltalk.chat.Test
import com.jorfald.smalltalk.database.UserDAO
import com.jorfald.smalltalk.database.UserObject
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


        val a = object : Test {
            override fun test2() {

            }
        }

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

    fun testCocktail(requestQueue: RequestQueue) {
        val url = "https://www.thecocktaildb.com/api/json/v1/1/random.php"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { jsonResponse ->
                val drinks = Gson().fromJson(jsonResponse, DrinkList::class.java)

                drinks.cocktails[0].ingredients
            },
            {

            }
        )

        requestQueue.add(stringRequest)
    }
}