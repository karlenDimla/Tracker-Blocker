package io.kdimla.trackblocker.browser.blocker

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient
import javax.inject.Inject

interface RequestInterceptor {
    fun intercept(
        requestedUrl: String?,
        request: WebResourceRequest
    ): WebResourceResponse?
}

class RequestInterceptorImpl @Inject constructor(
    private val thirdPartyDetector: ThirdPartyDetector,
    private val trackerDataClient: TrackerDataClient
) : RequestInterceptor {
    override fun intercept(
        requestedUrl: String?,
        request: WebResourceRequest
    ): WebResourceResponse? {
        val isThirdParty = thirdPartyDetector.isThirdParty(requestedUrl!!, request.url.toString())
        if (isThirdParty && trackerDataClient.matches(requestedUrl, request.url.toString())) {
            println("TRACKERBLOCKER BLOCKED: ${request.url} for $requestedUrl")
            return WebResourceResponse(null, null, null)
        }
        return null
    }
}
