package io.kdimla.trackblocker.fakes

import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource

class FakeRepository : TrackerDataRepository {
    var expectedTrackerList = listOf<Tracker>()
    override suspend fun saveTrackers(trackers: List<Tracker>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveProperties(properties: List<TrackerProperty>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveResources(resources: List<TrackerResource>) {
        TODO("Not yet implemented")
    }

    override fun areUrlsRelated(pageUrl: String, interceptedUrl: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun countTrackers(): Int {
        TODO("Not yet implemented")
    }

    override fun countProperties(): Int {
        TODO("Not yet implemented")
    }

    override fun getTracker(url: String): List<Tracker> = expectedTrackerList
}