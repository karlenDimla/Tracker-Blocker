package io.kdimla.trackblocker.browser.webview

import android.webkit.WebResourceResponse
import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient
import io.kdimla.trackblocker.trackerdata.source.disconnect.ThirdPartyDetector
import javax.inject.Inject

interface RequestInterceptor {
    fun intercept(
        pageUrl: String,
        interceptedUrl: String
    ): WebResourceResponse?
}

class RequestInterceptorImpl @Inject constructor(
    private val thirdPartyDetector: ThirdPartyDetector,
    private val trackerDataClient: TrackerDataClient
) : RequestInterceptor {
    override fun intercept(
        pageUrl: String,
        interceptedUrl: String
    ): WebResourceResponse? {
        println("TRACKERBLOCKER INTERCEPTED: $interceptedUrl for $pageUrl")
        val isThirdParty = thirdPartyDetector.isThirdParty(pageUrl, interceptedUrl)
        if (isThirdParty && trackerDataClient.matches(pageUrl, interceptedUrl)) {
            println("TRACKERBLOCKER BLOCKED: $interceptedUrl for $pageUrl")
            return WebResourceResponse(null, null, null)
        }
        return null
    }
}
