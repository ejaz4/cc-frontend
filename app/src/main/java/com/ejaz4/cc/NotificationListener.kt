package com.ejaz4.cc

import android.content.Context
import android.service.notification.NotificationListenerService
import android.util.Log

class NotificationListener: NotificationListenerService() {
    // A companion object is a good place for constants like the TAG
    companion object {
        private const val TAG = "NotificationListener_KT"
    }



    /**
     * Called when the listener is connected to the notification manager.
     */
    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.i(TAG, "Notification Listener connected (Kotlin).")
    }

}