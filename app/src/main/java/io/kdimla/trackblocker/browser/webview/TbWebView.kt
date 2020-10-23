package io.kdimla.trackblocker.browser.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView


@SuppressLint("SetJavaScriptEnabled")
class TbWebView : WebView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        settings.apply {
            javaScriptEnabled = true
            builtInZoomControls = true
            displayZoomControls = false
            useWideViewPort = true
            loadWithOverviewMode = true
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

            loadWithOverviewMode = true
            domStorageEnabled = true
        }

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptThirdPartyCookies(this, false)
        cookieManager.setAcceptCookie(false)
    }
}