package com.ejaz4.cc

import android.app.Notification
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.ejaz4.cc.libs.Vault

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

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn == null) return;

        val notification = sbn.notification;
        if (notification == null) return;


        val extras = notification.extras
        val style = extras.getString(Notification.EXTRA_TEMPLATE)
        val personName = extras.getString(Notification.EXTRA_TITLE);
        val messageContent = extras.getString(Notification.EXTRA_TEXT);

        if (personName == null) return;
        Log.d("NotificationListener", "Person Name: $personName");

        if (messageContent == null) return;
        Log.d("NotificationListener", "Message Content: $messageContent");

        // Check if the notification is a conversation
        val isConversation = notification.category == Notification.CATEGORY_MESSAGE || extras.containsKey(Notification.EXTRA_MESSAGES);
        if (!isConversation) return;

        // Additional checks can be performed for more detailed information.

        val messages = mutableListOf<String>()
        val parcelables = extras.getParcelableArray(Notification.EXTRA_MESSAGES)


        if (style == "android.app.Notification\$MessagingStyle") {
            Log.d("NotificationListener", "The notification uses MessagingStyle.")

            // For group conversations (API 28+)
            val isGroupConversation = extras.getBoolean("android.isGroupConversation")

            if (isGroupConversation) {
                Log.d("NotificationListener", "This is a group conversation.")
            } else {
                Log.d("NotificationListener", "This is a one-on-one conversation.")
            }

            if (parcelables != null) {
                for (parcelable in parcelables) {
                    if (parcelable is  Bundle) {
                        val messageBundle = parcelable
                        val messageText = messageBundle.getCharSequence("text")?.toString()
                        val senderPerson = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            messageBundle.getParcelable<android.app.Person>("sender_person")?.name
                        } else {
                            messageBundle.getCharSequence("sender")
                        }
                        Log.d("NotificationListener", "SenderPerson: $senderPerson")

                        if (messageText != null) {
                            messages.add("Sender: $senderPerson, Message: $messageText")
                            val vault = Vault();
                            vault.addToConversationBundle(this, "$senderPerson-${sbn.packageName}-${personName}", senderPerson.toString(), messageText.toString(), isGroupConversation, senderPerson.toString(), sbn.postTime)
                        }
                    }
                }
            }
            Log.d("NotificationListener", "Messages: $messages")
        }
    }


}