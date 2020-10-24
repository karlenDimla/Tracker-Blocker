package io.kdimla.trackblocker.browser.blocker

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient
import javax.inject.Inject

interface RequestInterceptor {
    fun intercept(
        pageUrl: String?,
        request: WebResourceRequest
    ): WebResourceResponse?
}

class RequestInterceptorImpl @Inject constructor(
    private val thirdPartyDetector: ThirdPartyDetector,
    private val trackerDataClient: TrackerDataClient
) : RequestInterceptor {
    override fun intercept(
        pageUrl: String?,
        request: WebResourceRequest
    ): WebResourceResponse? {
        val isThirdParty = thirdPartyDetector.isThirdParty(pageUrl!!, request.url.toString())
        if (isThirdParty && trackerDataClient.matches(pageUrl, request.url.toString())) {
            println("TRACKERBLOCKER BLOCKED: ${request.url} for $pageUrl")
            return WebResourceResponse(null, null, null)
        }
        return null
    }
}
