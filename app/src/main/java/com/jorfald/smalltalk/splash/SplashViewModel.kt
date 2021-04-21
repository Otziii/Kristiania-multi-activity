package com.jorfald.smalltalk.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorfald.smalltalk.UserManager
import com.jorfald.smalltalk.database.UserDAO
import com.jorfald.smalltalk.login.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val userRepository = UserRepository()

    fun checkIfUserIsLoggedIn(callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUser()

            callback(user != null)
        }
    }
}
