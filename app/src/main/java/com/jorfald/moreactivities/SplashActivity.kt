package com.jorfald.moreactivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jorfald.moreactivities.login.LoginActivity
import com.jorfald.moreactivities.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPref = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
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