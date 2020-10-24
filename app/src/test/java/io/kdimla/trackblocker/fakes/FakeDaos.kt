package io.kdimla.trackblocker.fakes

import io.kdimla.trackblocker.trackerdata.db.dao.TrackerDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerPropertyDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerResourceDao
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource

class FakeTrackerDao: TrackerDao() {
    var expectedTrackerList = listOf<Tracker>()
    override fun getAll(): List<Tracker> = expectedTrackerList

    override fun insertAll(trackers: List<Tracker>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getTracker(url: String): List<Tracker> = expectedTrackerList
}

class FakeTrackerResourceDao: TrackerResourceDao() {
    var shouldUrlBeAResource = false
    override fun getAll(): List<TrackerResource> {
        TODO("Not yet implemented")
    }

    override fun insertAll(resources: List<TrackerResource>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun countTrackerResource(resourceName: String, companyName: String): Int {
        return if(shouldUrlBeAResource) 1 else 0
    }
}

class FakeTrackerPropertyDao: TrackerPropertyDao() {
    var expectedPropertyList = listOf<TrackerProperty>()
    override fun getAll(): List<TrackerProperty> = expectedPropertyList

    override fun insertAll(properties: List<TrackerProperty>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getTrackerProperty(propertyName: String): List<TrackerProperty> = expectedPropertyList
}