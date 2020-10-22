package io.kdimla.trackblocker.browser.webview

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import io.kdimla.trackblocker.browser.blocker.RequestInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TbWebViewClient @Inject constructor(
    private val requestInterceptor: RequestInterceptor
) : WebViewClient() {

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        val url = runBlocking { withContext(Dispatchers.Main) { view.url } }
        return requestInterceptor.intercept(url, request)
    }
}