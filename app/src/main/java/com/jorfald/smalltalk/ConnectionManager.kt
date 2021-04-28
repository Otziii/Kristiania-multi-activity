package com.jorfald.smalltalk

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object ConnectionManager {
    fun isOnline(): Boolean {
        val applicationContext = SmallTalkApplication.application.applicationContext

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnected == true
    }
}