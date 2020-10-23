package io.kdimla.trackblocker.browser.blocker

import android.net.Uri
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerPropertyDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerResourceDao
import java.net.URL
import javax.inject.Inject

interface ThirdPartyDetector {
    fun isThirdParty(requestedUrl: String, interceptedUrl: String): Boolean
}

class ThirdPartyDetectorImpl @Inject constructor(
    private val trackerResourceDao: TrackerResourceDao,
    private val trackerPropertyDao: TrackerPropertyDao
) : ThirdPartyDetector {

    override fun isThirdParty(requestedUrl: String, interceptedUrl: String): Boolean {
        return !(isWithinSameDomain(requestedUrl, interceptedUrl) ||
                hasKnownRelation(requestedUrl, interceptedUrl))
    }

    private fun isWithinSameDomain(requestedUrl: String, interceptedUrl: String): Boolean {
        val requestedHost = Uri.parse(requestedUrl).host ?: ""
        val interceptedHost = Uri.parse(interceptedUrl).host ?: ""
        return requestedHost == interceptedHost || interceptedHost.endsWith(".$requestedHost")
    }

    private fun hasKnownRelation(requestedUrl: String, interceptedUrl: String): Boolean {
        val properties = trackerPropertyDao.getTrackerProperty(requestedUrl)
        if (properties.isEmpty()) return false
        properties.forEach {

            if (trackerResourceDao.countTrackerResource(interceptedUrl, it.company) > 0) {
                return true
            }
        }
        return false
    }

}