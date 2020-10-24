package io.kdimla.trackblocker.browser.blocker

import android.net.Uri
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerPropertyDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerResourceDao
import javax.inject.Inject

interface ThirdPartyDetector {
    fun isThirdParty(pageUrl: String, interceptedUrl: String): Boolean
}

class ThirdPartyDetectorImpl @Inject constructor(
    private val trackerResourceDao: TrackerResourceDao,
    private val trackerPropertyDao: TrackerPropertyDao
) : ThirdPartyDetector {

    override fun isThirdParty(pageUrl: String, interceptedUrl: String): Boolean {
        return !(isWithinSameDomain(pageUrl, interceptedUrl) ||
                hasKnownRelation(pageUrl, interceptedUrl))
    }

    private fun isWithinSameDomain(pageUrl: String, interceptedUrl: String): Boolean {
        val requestedHost = Uri.parse(pageUrl).host ?: ""
        val interceptedHost = Uri.parse(interceptedUrl).host ?: ""
        return requestedHost == interceptedHost || interceptedHost.endsWith(".$requestedHost")
    }

    private fun hasKnownRelation(pageUrl: String, interceptedUrl: String): Boolean {
        val properties = trackerPropertyDao.getTrackerProperty(pageUrl)
        if (properties.isEmpty()) return false
        properties.forEach {

            if (trackerResourceDao.countTrackerResource(interceptedUrl, it.company) > 0) {
                return true
            }
        }
        return false
    }

}