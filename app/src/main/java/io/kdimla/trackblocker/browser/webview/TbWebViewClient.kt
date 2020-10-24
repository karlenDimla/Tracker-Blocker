package io.kdimla.trackblocker.browser.webview

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import javax.inject.Inject

class TbWebViewClient @Inject constructor(
    private val requestInterceptor: RequestInterceptor
) : WebViewClient() {
    private var lastRequestedPageUrl: String = ""

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        lastRequestedPageUrl = request?.url?.host ?: ""
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        val interceptedUrl = request.url.host ?: ""
        return requestInterceptor.intercept(lastRequestedPageUrl, interceptedUrl)
    }
}