package io.kdimla.trackblocker.trackerdata.source.disconnect

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.DefaultTrackerDataRepository
import io.kdimla.trackblocker.trackerdata.source.TrackerDataDownloader
import io.kdimla.trackblocker.trackerdata.source.endpoint.TrackerDataEndpoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@WorkerThread
class DisconnectDataDownloader @Inject constructor(
    private val endpoint: TrackerDataEndpoint,
    private val dbHelper: DefaultTrackerDataRepository
) : TrackerDataDownloader {
    override fun downloadData(): Boolean {
        val trackers = endpoint.getTrackerServices().execute().body()?.trackers ?: listOf()
        if (trackers.isNotEmpty()) {
            runBlocking {
                dbHelper.saveTrackers(trackers)
            }
        }
        return true
    }
}