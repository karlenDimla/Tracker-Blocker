package io.kdimla.trackblocker.browser.webview

import android.webkit.WebResourceResponse
import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient
import io.kdimla.trackblocker.trackerdata.source.disconnect.ThirdPartyDetector
import timber.log.Timber
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
        val isThirdParty = thirdPartyDetector.isThirdParty(pageUrl, interceptedUrl)
        if (isThirdParty && trackerDataClient.matches(pageUrl, interceptedUrl)) {
            Timber.tag("Tracker blocking").d("Blocked $interceptedUrl for $pageUrl");
            return WebResourceResponse(null, null, null)
        }
        return null
    }
}
