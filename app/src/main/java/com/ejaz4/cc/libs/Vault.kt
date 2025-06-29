package com.ejaz4.cc.libs

import android.content.Context
import java.util.Date
import kotlinx.serialization.Serializable;
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.core.content.edit
import kotlinx.serialization.decodeFromString



class Vault() {
    @Serializable
    data class Message(val id: String, val sender: String, val message: String, val isGroup: Boolean, val conversationName: String?, val timestamp: Long)


    public fun addToConversationBundle(ctx: Context, id: String, sender: String, message: String, isGroup: Boolean, conversationName: String?, timestamp: Long) {
        val sharedPref = ctx.getSharedPreferences("com.ejaz4.cc", Context.MODE_PRIVATE) ?: return
        val conversationId = "$id-unsent-bundle";
        if (!sharedPref.contains(conversationId)) {
            sharedPref.edit {
                putString(conversationId, "[]");
            }
        }

        // Get current unsent bundle

        val bundleUnserialised: String = sharedPref.getString(id, "[]") as String;

        val bundleSerialised = Json.decodeFromString<List<Message>>(bundleUnserialised)

        val newBundle = bundleSerialised.plus(Message(id, sender, message, isGroup, conversationName, timestamp));

        sharedPref.edit{
            putString(conversationId, Json.encodeToString<List<Message>>(newBundle));
        }

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

    public fun getConversationBundle(ctx: Context, id: String): List<String> {
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
}