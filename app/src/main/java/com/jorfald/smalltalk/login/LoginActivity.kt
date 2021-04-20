package com.jorfald.smalltalk.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jorfald.smalltalk.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportFragmentManager.beginTransaction()
            .replace(R.id.login_container, LoginFragment())
            .commitNow()
    }
}