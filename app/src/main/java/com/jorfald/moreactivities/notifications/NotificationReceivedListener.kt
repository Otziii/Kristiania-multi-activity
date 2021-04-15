package com.jorfald.moreactivities.notifications

class NotificationReceivedListener {
    var listener: OnNotificationReceivedListener? = null

    interface OnNotificationReceivedListener{
        fun onNotificationReceived()
    }

    fun setOnNotificationReceivedListener(param: OnNotificationReceivedListener) {
        listener = param
    }

    fun notificationReceived(){
        listener?.onNotificationReceived()
    }

}