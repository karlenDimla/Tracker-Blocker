package io.kdimla.trackblocker.trackerdata.db

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerPropertyDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerResourceDao
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource
import javax.inject.Inject

@WorkerThread
interface TrackerDataDBHelper {
    suspend fun saveTrackers(trackers: List<Tracker>)
    suspend fun saveProperties(properties: List<TrackerProperty>)
    suspend fun saveResources(resources: List<TrackerResource>)
    fun countTrackers(): Int
    fun countProperties(): Int
}

class DefaultTrackerDataDBHelper @Inject constructor(
    private val trackerDao: TrackerDao,
    private val resourceDao: TrackerResourceDao,
    private val propertyDao: TrackerPropertyDao
) : TrackerDataDBHelper {

    override suspend fun saveTrackers(trackers: List<Tracker>) {
        trackerDao.updateAll(trackers)
    }

    override suspend fun saveProperties(properties: List<TrackerProperty>) {
        propertyDao.updateAll(properties)
    }

    override suspend fun saveResources(resources: List<TrackerResource>) {
        resourceDao.updateAll(resources)
    }

    override fun countTrackers(): Int = trackerDao.getAll().size

    override fun countProperties(): Int = propertyDao.getAll().size
}