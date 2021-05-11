package com.jorfald.smalltalk.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorfald.smalltalk.database.UserObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class LoginViewModel : ViewModel() {

    val loginSuccess = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    private val userRepository = UserRepository()

    open fun logInUser(
        username: String,
        password: String
    ) {
        isLoading.postValue(true)

        userRepository.logInUser(
            username,
            password
        ) { user ->
            if (user != null) {
                saveUser(user)
            } else {
                loginSuccess.postValue(false)
                isLoading.postValue(false)
            }
        }
    }

    private fun saveUser(user: UserObject) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.saveUser(user)

            loginSuccess.postValue(true)
        }
    }
}