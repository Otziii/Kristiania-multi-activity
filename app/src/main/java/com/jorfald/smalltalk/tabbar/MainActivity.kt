package com.jorfald.smalltalk.tabbar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorfald.smalltalk.R
import com.jorfald.smalltalk.UserManager
import com.jorfald.smalltalk.database.AppDatabase
import com.jorfald.smalltalk.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    fun logOutUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val chatDAO = AppDatabase.getDatabase(this@MainActivity).chatDAO()
            chatDAO.deleteAllMessages()

            val userDAO = AppDatabase.getDatabase(this@MainActivity).userDAO()
            userDAO.deleteUser(UserManager.loggedInUser)

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }
    }
}