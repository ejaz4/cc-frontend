package com.ejaz4.cc.libs

import android.content.Context
import android.widget.Toast

class MAL(private val ctx: Context) {
    @android.webkit.JavascriptInterface
    fun toastMessage(message: String) {
        val toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.show()
    }
}