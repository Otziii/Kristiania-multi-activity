package com.jorfald.moreactivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jorfald.moreactivities.login.LoginActivity
import com.jorfald.moreactivities.splash.SplashViewModel
import com.jorfald.moreactivities.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //TODO: Read user object from DB to see if the user is logged in

        val sharedPref =
            getSharedPreferences(LoginActivity.SHARED_PREF_FILENAME, Context.MODE_PRIVATE)
        val userIsLoggedIn = sharedPref.getBoolean(LoginActivity.LOGGED_IN_KEY, false)

        val activityIntent = if (userIsLoggedIn) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        activityIntent.flags = activityIntent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(activityIntent)
    }
}