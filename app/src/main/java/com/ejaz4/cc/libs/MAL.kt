package com.ejaz4.cc.libs

import android.content.Context
import android.widget.Toast
import androidx.core.content.edit
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

    @android.webkit.JavascriptInterface
    fun updateConversationState(id: String, state: String) {
        vault.updateConversationState(ctx, id, state);
    }

    @android.webkit.JavascriptInterface
    fun getBundle(id: String): String {
        return Json.encodeToString(vault.getBundle(ctx, id));
    }

    @android.webkit.JavascriptInterface
    fun setSummary( id: String, extract: String) {
        vault.setSummary(ctx, id, extract)
    }

    @android.webkit.JavascriptInterface
    fun getSummary(id: String): String {
        return vault.getSummary(ctx, id)
    }
}