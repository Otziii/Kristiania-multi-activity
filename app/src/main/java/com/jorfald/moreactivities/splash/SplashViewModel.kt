package com.jorfald.moreactivities.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorfald.moreactivities.UserManager
import com.jorfald.moreactivities.database.UserDAO
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
