package com.ejaz4.cc.libs

import android.content.Context
import java.util.Date
import kotlinx.serialization.Serializable;
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.core.content.edit
import kotlinx.serialization.decodeFromString

@Serializable
public data class Message(val id: String, val sender: String, val message: String, val isGroup: Boolean, val conversationName: String?, val timestamp: Long)


class Vault() {


    public fun addToConversationBundle(ctx: Context, id: String, sender: String, message: String, isGroup: Boolean, conversationName: String?, timestamp: Long) {
        var new = false;
        val sharedPref = ctx.getSharedPreferences("com.ejaz4.cc", Context.MODE_PRIVATE) ?: return
        val conversationId = "$id-unsent-bundle";
        if (!sharedPref.contains(conversationId)) {
            sharedPref.edit {
                putString(conversationId, "[]");
            }
            new = true;
        }

        // Get current unsent bundle

        val bundleUnserialised: String = sharedPref.getString(conversationId, "[]") as String;

        val bundleSerialised = Json.decodeFromString<List<Message>>(bundleUnserialised)

        val newBundle = bundleSerialised.plus(Message(id, sender, message, isGroup, conversationName, timestamp));

        sharedPref.edit{
            putString(conversationId, Json.encodeToString<List<Message>>(newBundle));
        }

        sharedPref.edit() {
            putString("$id-status", "unsent")
        }

        if (!new) return;

        val conversations = "conversations";
        if (!sharedPref.contains(conversations)) {
            sharedPref.edit {
                putString(conversations, "[]");
            }
        }
        val conversationsUnserialised: String = sharedPref.getString(conversations, "[]") as String;
        val conversationsSerialised = Json.decodeFromString<List<String>>(conversationsUnserialised)

        val newConversations = conversationsSerialised.plus(id);

        sharedPref.edit() {
            putString(conversations, Json.encodeToString(newConversations));
        }


    }

    public fun getBundle(ctx: Context, id: String): List<Message> {
        val sharedPref = ctx.getSharedPreferences("com.ejaz4.cc", Context.MODE_PRIVATE) ?: return listOf()
        val conversationId = "$id-unsent-bundle";

        val bundleUnserialised: String = sharedPref.getString(conversationId, "[]") as String;
        val bundleSerialised = Json.decodeFromString<List<Message>>(bundleUnserialised)

        return bundleSerialised
    }

    public fun getConversationBundle(ctx: Context): List<String> {
        val sharedPref = ctx.getSharedPreferences("com.ejaz4.cc", Context.MODE_PRIVATE) ?: return listOf()

        val conversations = "conversations";
        if (!sharedPref.contains(conversations)) {
            sharedPref.edit {
                putString(conversations, "[]");
            }
        }

        val conversationsUnserialised: String = sharedPref.getString(conversations, "[]") as String;
        val conversationsSerialised = Json.decodeFromString<List<String>>(conversationsUnserialised)

        return conversationsSerialised
    }

    public fun getConversationState(ctx: Context, id: String): String {
        val sharedPref = ctx.getSharedPreferences("com.ejaz4.cc", Context.MODE_PRIVATE) ?: return "unknown";

        return sharedPref.getString("$id-status", "unknown") as String;
    }

    public fun updateConversationState(ctx: Context, id: String, state: String) {
        val sharedPref = ctx.getSharedPreferences("com.ejaz4.cc", Context.MODE_PRIVATE) ?: return;

        sharedPref.edit {
            putString("$id-status", state);
        }
    }

    public fun setSummary(ctx: Context, id: String, extract: String) {
        val sharedPref = ctx.getSharedPreferences("com.ejaz4.cc", Context.MODE_PRIVATE) ?: return;
        val key = "$id-summary-extract";

        sharedPref.edit {
            putString(key, extract);
        }

        sharedPref.edit() {
            putString("$id-status", "done")
        }

        sharedPref.edit() {
            putString("$id-unsent-bundle", "[]")
        }
    }

    public fun getSummary(ctx: Context, id: String): String {
        val sharedPref = ctx.getSharedPreferences("com.ejaz4.cc", Context.MODE_PRIVATE) ?: return "";
        val key = "$id-summary-extract";

        return sharedPref.getString(key, "") as String
    }
}