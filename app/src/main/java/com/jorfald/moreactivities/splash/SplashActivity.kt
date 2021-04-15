package com.jorfald.moreactivities.splash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.jorfald.moreactivities.R
import com.jorfald.moreactivities.notifications.SmallTalkFirebaseMessagingService
import com.jorfald.moreactivities.database.AppDatabase
import com.jorfald.moreactivities.login.LoginActivity
import com.jorfald.moreactivities.tabbar.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userDao = AppDatabase.getDatabase(this).userDAO()
        splashViewModel.checkIfUserIsLoggedIn(userDao) { isLoggedIn ->
            val activityIntent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }

            activityIntent.flags = activityIntent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(activityIntent)
        }

        // Has App started with a Notification?
        intent.extras?.let {
            (it["user_id"] as? String)?.let { userId ->
                // Go directly to the User Page, for example
                Log.d("SplashActivity", "User_id: $userId")
            }
        }

        registerNotificationChannel()
        subscribeDeviceTokenToPushNotifications()
    }

    private fun registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Once created, channel can't be modified - to change priority you have to recreate it
            //val channel = NotificationChannel("CHANNEL", "Push Notifications", NotificationManager.IMPORTANCE_LOW)
            val channel = NotificationChannel("CHANNEL_1", "Push Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun subscribeDeviceTokenToPushNotifications() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            task.result?.let {
                Log.d("TAG", it)
                SmallTalkFirebaseMessagingService.subscribeDeviceTokenToPushNotifications(this, it)
            }
        }
    }
}