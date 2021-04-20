package com.jorfald.smalltalk.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorfald.smalltalk.UserManager
import com.jorfald.smalltalk.database.UserDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    fun checkIfUserIsLoggedIn(userDao: UserDAO, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getUser()

            if (user != null) {
                UserManager.loggedInUser = user
            }

            callback(user != null)
        }
    }
}
