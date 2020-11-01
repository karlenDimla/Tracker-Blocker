package io.kdimla.trackblocker.trackerdata.source.disconnect

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import javax.inject.Inject

interface ThirdPartyDetector {
    fun isThirdParty(pageUrl: String, interceptedUrl: String): Boolean
}

@WorkerThread
class DisconnectThirdPartyDetectorImpl @Inject constructor(
    private val trackerDataRepository: TrackerDataRepository
) : ThirdPartyDetector {

    override fun isThirdParty(pageUrl: String, interceptedUrl: String): Boolean {
        val pageUrlHost = pageUrl.removeSubdomain()
        val interceptedHost = interceptedUrl.removeSubdomain()
        return !(isWithinSameDomain(pageUrlHost, interceptedUrl) ||
                trackerDataRepository.areUrlsRelated(pageUrlHost, interceptedHost))
    }

    private fun isWithinSameDomain(pageUrl: String, interceptedUrl: String): Boolean {
        return pageUrl == interceptedUrl || interceptedUrl.endsWith(".${pageUrl}")
    }

    // TODO find a fix to remove subdomain. This doesn't work for "co.uk" etc
    private fun String.removeSubdomain(): String {
        val initialString = this
        return split(".").run {
            val lastIndex = this.size - 1
            if (lastIndex > 1) {
                this[lastIndex - 1] + "." + this[lastIndex]
            } else {
                initialString
            }

        }
    }
}