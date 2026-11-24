package com.elking.routercontrol

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private lateinit var status: TextView
    private val routerUrl = "http://192.168.1.1/"
    private val routerHost = "192.168.1.1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(12, 13, 16))
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 14, 18, 10)
            setBackgroundColor(Color.rgb(18, 19, 23))
        }

        val title = TextView(this).apply {
            text = "EL-KING  •  ROUTER CONTROL"
            textSize = 18f
            setTextColor(Color.rgb(224, 185, 103))
            gravity = Gravity.CENTER
        }

        status = TextView(this).apply {
            text = "جاهز لفتح صفحة الراوتر"
            textSize = 13f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 7, 0, 4)
        }

        val controls = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(8, 4, 8, 8)
        }

        fun makeButton(label: String, action: () -> Unit): Button {
            return Button(this).apply {
                text = label
                setTextColor(Color.BLACK)
                setBackgroundColor(Color.rgb(218, 178, 91))
                isAllCaps = false
                setOnClickListener { action() }
                layoutParams = LinearLayout.LayoutParams(0, 46, 1f).apply {
                    setMargins(4, 0, 4, 0)
                }
            }
        }

        webView = WebView(this).apply {
            setBackgroundColor(Color.WHITE)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true
            settings.allowFileAccess = false
            settings.allowContentAccess = false
            settings.javaScriptCanOpenWindowsAutomatically = false
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val uri = request.url
                    val allowed = uri.scheme == "http" && uri.host == routerHost
                    if (!allowed) {
                        status.text = "تم منع الانتقال إلى عنوان خارج صفحة الراوتر"
                    }
                    return !allowed
                }

                override fun onPageStarted(view: WebView, url: String, favicon: android.graphics.Bitmap?) {
                    status.text = "جارٍ الاتصال بصفحة الراوتر..."
                }

                override fun onPageFinished(view: WebView, url: String) {
                    status.text = "تم تحميل صفحة الراوتر داخل التطبيق"
                }

                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest,
                    error: android.webkit.WebResourceError
                ) {
                    if (request.isForMainFrame) {
                        status.text = "تعذر الوصول. تأكد من اتصالك بشبكة الراوتر."
                    }
                }
            }
            webChromeClient = WebChromeClient()
        }

        controls.addView(makeButton("رجوع") {
            if (webView.canGoBack()) webView.goBack()
        })
        controls.addView(makeButton("تحديث") { webView.reload() })
        controls.addView(makeButton("الرئيسية") { webView.loadUrl(routerUrl) })

        header.addView(title)
        header.addView(status)
        header.addView(controls)

        root.addView(header, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ))
        root.addView(webView, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        setContentView(root)
        webView.loadUrl(routerUrl)
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        if (::webView.isInitialized) {
            webView.stopLoading()
            webView.destroy()
        }
        super.onDestroy()
    }
}
