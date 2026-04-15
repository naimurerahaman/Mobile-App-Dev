package com.example.learningportalapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.webkit.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlBar: EditText
    private lateinit var loadProgressBar: ProgressBar
    private lateinit var backBtn: ImageButton
    private lateinit var forwardBtn: ImageButton
    private lateinit var refreshBtn: ImageButton
    private lateinit var homeBtn: ImageButton
    private lateinit var goBtn: Button

    private val homeUrl = "https://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        urlBar = findViewById(R.id.urlBar)
        loadProgressBar = findViewById(R.id.loadProgressBar)
        backBtn = findViewById(R.id.backBtn)
        forwardBtn = findViewById(R.id.forwardBtn)
        refreshBtn = findViewById(R.id.refreshBtn)
        homeBtn = findViewById(R.id.homeBtn)
        goBtn = findViewById(R.id.goBtn)

        // WebView settings
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        // Prevent opening external browser
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: android.graphics.Bitmap?) {
                loadProgressBar.visibility = android.view.View.VISIBLE
                urlBar.setText(url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                loadProgressBar.visibility = android.view.View.GONE
                urlBar.setText(url)
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                // Show offline page
                webView.loadUrl("file:///android_asset/offline.html")
            }
        }

        // Track loading progress
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                loadProgressBar.progress = newProgress
            }
        }

        // Load default page
        loadUrl(homeUrl)

        // Toolbar buttons
        backBtn.setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
            else Toast.makeText(this, "No more history", Toast.LENGTH_SHORT).show()
        }

        forwardBtn.setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }

        refreshBtn.setOnClickListener { webView.reload() }

        homeBtn.setOnClickListener { loadUrl(homeUrl) }

        goBtn.setOnClickListener { loadFromBar() }

        // Load on keyboard "Done" / "Go"
        urlBar.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                loadFromBar()
                true
            } else false
        }

        // Shortcut buttons
        findViewById<Button>(R.id.btnGoogle).setOnClickListener { loadUrl("https://www.google.com") }
        findViewById<Button>(R.id.btnYouTube).setOnClickListener { loadUrl("https://www.youtube.com") }
        findViewById<Button>(R.id.btnWikipedia).setOnClickListener { loadUrl("https://www.wikipedia.org") }
        findViewById<Button>(R.id.btnKhan).setOnClickListener { loadUrl("https://www.khanacademy.org") }
        findViewById<Button>(R.id.btnUniversity).setOnClickListener { loadUrl("https://www.google.com") }
    }

    private fun loadFromBar() {
        var url = urlBar.text.toString().trim()
        if (url.isEmpty()) return
        // Add https:// if missing
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://$url"
        }
        loadUrl(url)
    }

    private fun loadUrl(url: String) {
        if (isOnline()) {
            webView.loadUrl(url)
        } else {
            webView.loadUrl("file:///android_asset/offline.html")
        }
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }

    // Handle Android back button for WebView history
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}