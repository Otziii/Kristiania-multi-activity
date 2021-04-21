package com.jorfald.smalltalk.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jorfald.smalltalk.R
import com.jorfald.smalltalk.database.AppDatabase
import com.jorfald.smalltalk.login.LoginActivity
import com.jorfald.smalltalk.tabbar.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.checkIfUserIsLoggedIn { isLoggedIn ->
            val activityIntent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }

            activityIntent.flags = activityIntent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(activityIntent)
        }
    }
}