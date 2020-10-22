package io.kdimla.trackblocker.browser.blocker

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import javax.inject.Inject

interface RequestInterceptor {
    fun intercept(
        requestedUrl: String?,
        request: WebResourceRequest
    ): WebResourceResponse?
}

class RequestInterceptorImpl @Inject constructor() :
    RequestInterceptor {
    override fun intercept(
        requestedUrl: String?,
        request: WebResourceRequest
    ): WebResourceResponse? {
        return null
    }
}
