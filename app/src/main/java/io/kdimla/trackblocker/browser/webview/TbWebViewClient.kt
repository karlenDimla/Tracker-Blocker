package io.kdimla.trackblocker.browser.webview

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import timber.log.Timber
import javax.inject.Inject

class TbWebViewClient @Inject constructor(
    private val requestInterceptor: RequestInterceptor
) : WebViewClient() {
    private var lastRequestedPageUrl: String = ""

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        lastRequestedPageUrl = request?.url?.host ?: ""
        return super.shouldOverrideUrlLoading(view, request)
    }

    fun setPageUrl(newPageUrl: String) {
        this.lastRequestedPageUrl = Uri.parse(newPageUrl).host ?: ""
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        val interceptedUrl = request.url.host ?: ""
        Timber.tag("Tracker blocking").d("Intercepted ${request.url} for $lastRequestedPageUrl")
        return requestInterceptor.intercept(lastRequestedPageUrl, interceptedUrl)
    }
}