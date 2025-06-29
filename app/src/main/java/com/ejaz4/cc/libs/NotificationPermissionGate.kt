package com.ejaz4.cc.libs

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast


class NotificationPermissionGate {
    public fun isNotificationServiceEnabled(c: Context): Boolean {
        val pkgName = c.getPackageName()
        val flat: String? = Settings.Secure.getString(
            c.getContentResolver(),
            "enabled_notification_listeners"
        )
        if (!TextUtils.isEmpty(flat)) {
            val names: Array<String?> =
                flat!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i]!!)
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true
                    }
                }
            }
        }
        return false
    }

    public fun launchNotificationServiceEnablerSettings(c: Context) {

        // This intent will open the "Notification access" settings screen.
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)


        // It's good practice to add this flag if you are calling startActivity
        // from a non-Activity context.
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val toast = Toast.makeText(c, "Now go to CC and enable the Notification Listener Service",
            Toast.LENGTH_LONG) // in Activity
        toast.show()
        c.startActivity(intent)

    }
}