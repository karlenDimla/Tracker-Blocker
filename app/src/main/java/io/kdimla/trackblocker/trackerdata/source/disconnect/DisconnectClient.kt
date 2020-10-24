package io.kdimla.trackblocker.trackerdata.source.disconnect

import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient
import javax.inject.Inject

class DisconnectClient @Inject constructor(
    private val trackerDataRepository: TrackerDataRepository
) : TrackerDataClient {
    override fun matches(pageUrl: String, interceptedUrl: String): Boolean {
        val hostMinusSubDomain = interceptedUrl.removeSubdomain()
        val matches = trackerDataRepository.getTracker(hostMinusSubDomain)
        return matches.isNotEmpty()
    }

    // TODO find a fix to remove subdomain. This doesn't work for "co.uk" etc
    private fun String.removeSubdomain(): String {
        return split(".").run {
            val lastIndex = this.size - 1
            this[lastIndex - 1] + "." + this[lastIndex]
        }
    }
}