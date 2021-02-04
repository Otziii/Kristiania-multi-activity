package com.jorfald.moreactivities.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jorfald.moreactivities.R

class LoginActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREF_FILENAME = "shared_prefs_file"
        const val LOGGED_IN_KEY = "USER_IS_LOGGED_IN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportFragmentManager.beginTransaction()
            .replace(R.id.login_container, LoginFragment())
            .commitNow()
    }
}