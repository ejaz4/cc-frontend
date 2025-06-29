package com.ejaz4.cc.libs

import android.content.Context
import android.widget.Toast
import kotlinx.serialization.json.Json

class MAL(private val ctx: Context) {
    val vault = Vault();

    @android.webkit.JavascriptInterface
    fun toastMessage(message: String) {
        val toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.show()
    }

    @android.webkit.JavascriptInterface
    fun getConversationIDs(): String {
        return Json.encodeToString(vault.getConversationBundle(ctx));
    }


    @android.webkit.JavascriptInterface
    fun getConversationStatus(id: String): String {
        return vault.getConversationState(ctx, id);
    }
}