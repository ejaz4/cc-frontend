package com.ejaz4.cc
import android.content.Context
import android.util.Log
import fi.iki.elonen.NanoHTTPD

class Server(private val context: Context, port: Int) : NanoHTTPD(port)  {
    override fun serve(session: IHTTPSession): Response {
        var uri = session.uri.removePrefix("/").ifEmpty { "index.html" }
        println("Loading $uri")
        if ("." !in uri) {
            uri += ".html"
        }

        try {
            val mime = when (uri.substringAfterLast(".")) {
                "ico" -> "image/x-icon"
                "css" -> "text/css"
                "htm" -> "text/html"
                "html" -> "text/html"
                "svg" -> "image/svg+xml"
                else -> "application/javascript"
            }

            return NanoHTTPD.newChunkedResponse(
                Response.Status.OK,
                mime,
                context.assets.open(uri) // prefix with www because your files are not in the root folder in assets
            )
        } catch (e: Exception) {
            val message = "Failed to load asset $uri because $e"
            Log.e("Webserver", message)
            e.printStackTrace()
            return NanoHTTPD.newFixedLengthResponse(message)
        }
    }
}