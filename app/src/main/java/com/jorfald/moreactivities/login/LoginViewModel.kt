package com.jorfald.moreactivities.login

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    fun correctCredentials(username: String, password: String): Boolean {
        return username == "øivind" && password == "1234"
    }
}