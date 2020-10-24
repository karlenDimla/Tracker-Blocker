package io.kdimla.trackblocker.browser.webview

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import io.kdimla.trackblocker.browser.blocker.RequestInterceptor
import javax.inject.Inject

class TbWebViewClient @Inject constructor(
    private val requestInterceptor: RequestInterceptor
) : WebViewClient() {
    private var lastRequestedPageUrl: String? = ""

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        lastRequestedPageUrl = request?.url.toString()
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        return requestInterceptor.intercept(lastRequestedPageUrl, request)
    }
}