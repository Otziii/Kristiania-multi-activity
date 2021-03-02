package com.jorfald.moreactivities.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jorfald.moreactivities.R
import com.jorfald.moreactivities.login.LoginActivity
import com.jorfald.moreactivities.tabbar.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //TODO: Read user object from DB to see if the user is logged in

        val activityIntent = Intent(this, MainActivity::class.java)
        Intent(this, LoginActivity::class.java)

        activityIntent.flags = activityIntent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(activityIntent)
    }
}