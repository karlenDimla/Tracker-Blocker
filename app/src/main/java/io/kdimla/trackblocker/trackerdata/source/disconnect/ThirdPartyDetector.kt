package io.kdimla.trackblocker.trackerdata.source.disconnect

import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import javax.inject.Inject

interface ThirdPartyDetector {
    fun isThirdParty(pageUrl: String, interceptedUrl: String): Boolean
}

class DisconnectThirdPartyDetectorImpl @Inject constructor(
    private val trackerDataRepository: TrackerDataRepository
) : ThirdPartyDetector {

    override fun isThirdParty(pageUrl: String, interceptedUrl: String): Boolean {
        return !(isWithinSameDomain(pageUrl, interceptedUrl) ||
                trackerDataRepository.areUrlsRelated(pageUrl, interceptedUrl))
    }

    private fun isWithinSameDomain(pageUrl: String, interceptedUrl: String): Boolean {
        return pageUrl == interceptedUrl || interceptedUrl.endsWith(".$pageUrl")
    }
}