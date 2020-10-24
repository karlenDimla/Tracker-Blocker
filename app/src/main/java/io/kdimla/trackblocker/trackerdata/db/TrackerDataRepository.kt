package io.kdimla.trackblocker.trackerdata.db

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerPropertyDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerResourceDao
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource
import javax.inject.Inject
import javax.inject.Singleton

@WorkerThread
interface TrackerDataRepository {
    suspend fun loadData();
    suspend fun saveTrackers(trackers: List<Tracker>)
    suspend fun saveProperties(properties: List<TrackerProperty>)
    suspend fun saveResources(resources: List<TrackerResource>)
    fun areUrlsRelated(pageUrl: String, interceptedUrl: String): Boolean
    fun countTrackers(): Int
    fun countProperties(): Int
    fun getTracker(url: String): List<Tracker>
}

@WorkerThread
@Singleton
class DefaultTrackerDataRepository @Inject constructor(
    private val trackerDao: TrackerDao,
    private val resourceDao: TrackerResourceDao,
    private val propertyDao: TrackerPropertyDao
) : TrackerDataRepository {

    private var trackerList: List<Tracker> = listOf()
    private var propertyList: List<TrackerProperty> = listOf()
    private var resourceList: List<TrackerResource> = listOf()

    override suspend fun loadData() {
        if (trackerList.isEmpty()) trackerList = trackerDao.getAll()
        if (propertyList.isEmpty()) propertyList = propertyDao.getAll()
        if (resourceList.isEmpty()) resourceList = resourceDao.getAll()
    }

    override suspend fun saveTrackers(trackers: List<Tracker>) {
        trackerDao.updateAll(trackers)
        trackerList = trackers
    }

    override suspend fun saveProperties(properties: List<TrackerProperty>) {
        propertyDao.updateAll(properties)
        propertyList = properties
    }

    override suspend fun saveResources(resources: List<TrackerResource>) {
        resourceDao.updateAll(resources)
        resourceList = resources
    }

    override fun areUrlsRelated(pageUrl: String, interceptedUrl: String): Boolean {
        val properties = getProperty(pageUrl)
        if (properties.isEmpty()) return false
        properties.forEach {
            if (countResource(interceptedUrl, it.company) > 0) {
                return true
            }
        }
        return false
    }

    private fun countResource(interceptedUrl: String, company: String): Int {
        return if (resourceList.isEmpty()) {
            resourceDao.countTrackerResource(interceptedUrl, company)
        } else {
            resourceList.filter {
                it.company == company && it.resource == interceptedUrl
            }.size
        }
    }

    private fun getProperty(pageUrl: String): List<TrackerProperty> {
        return if (propertyList.isEmpty()) {
            propertyDao.getTrackerProperty(pageUrl)
        } else {
            propertyList.filter { it.property == pageUrl }
        }
    }

    override fun countTrackers(): Int {
        return if (trackerList.isEmpty()) {
            trackerDao.getAll().size
        } else {
            trackerList.size
        }
    }

    override fun countProperties(): Int {
        return if (propertyList.isEmpty()) {
            propertyDao.getAll().size
        } else {
            propertyList.size
        }
    }

    override fun getTracker(url: String): List<Tracker> {
        return if (trackerList.isEmpty()) {
            trackerDao.getTracker(url)
        } else {
            trackerList.filter {
                it.url == url
            }
        }
    }

}