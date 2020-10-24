package io.kdimla.trackblocker.fakes

import io.kdimla.trackblocker.trackerdata.source.disconnect.ThirdPartyDetector

class FakeThirdPartyDetector: ThirdPartyDetector {
    var shouldBeThirdParty = false
    var paramPageUrl: String? = ""
    var paramInterceptedUrl: String? = ""
    override fun isThirdParty(pageUrl: String, interceptedUrl: String): Boolean {
        paramPageUrl = pageUrl
        paramInterceptedUrl = interceptedUrl
        return shouldBeThirdParty
    }
}
