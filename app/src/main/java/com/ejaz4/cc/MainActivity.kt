package com.ejaz4.cc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ejaz4.cc.libs.MAL
import com.ejaz4.cc.libs.NotificationPermissionGate

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val notificationsEnabled: Boolean = NotificationPermissionGate().isNotificationServiceEnabled(this);

        if (!notificationsEnabled) {
            val permissionGranterIntent = Intent(this, PermissionGranter::class.java);
            finish()
            startActivity(permissionGranterIntent);
        }

        var port = 17346
        var ws = Server(applicationContext, port)

        ws.start()

        var sbx: WebView = findViewById(R.id.sandbox)

        sbx.settings.javaScriptEnabled = true;
        sbx.settings.domStorageEnabled = true;
        sbx.settings.databaseEnabled = true;
        sbx.settings.builtInZoomControls = false;

        sbx.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true;
            }
        }

        sbx.addJavascriptInterface(MAL(this), "Android")

        sbx.loadUrl("http://localhost:$port/index.html")
    }
}