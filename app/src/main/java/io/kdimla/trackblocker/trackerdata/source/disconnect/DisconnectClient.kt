package io.kdimla.trackblocker.trackerdata.source.disconnect

import android.net.Uri
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerDao
import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient
import javax.inject.Inject

class DisconnectClient @Inject constructor(
    private val trackerDao: TrackerDao
) : TrackerDataClient {
    override fun matches(pageUrl: String, interceptedUrl: String): Boolean {
        val host = Uri.parse(interceptedUrl)?.host ?: ""
        val hostMinusSubDomain = host.removeSubdomain()
        val matches = trackerDao.getTracker(hostMinusSubDomain)
        return matches.isNotEmpty()
    }

    private fun String.removeSubdomain(): String {
        return split(".").run {
            val lastIndex = this.size -1
            this[lastIndex - 1] + "." + this[lastIndex]
        }
    }
}