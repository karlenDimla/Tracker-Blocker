package io.kdimla.trackblocker.trackerdata.source.disconnect

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import io.kdimla.trackblocker.trackerdata.source.TrackerDataDownloader
import io.kdimla.trackblocker.trackerdata.source.endpoint.TrackerDataEndpointInteractor
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@WorkerThread
class DisconnectDataDownloader @Inject constructor(
    private val endpointInteractor: TrackerDataEndpointInteractor,
    private val repository: TrackerDataRepository
) : TrackerDataDownloader {
    override fun downloadData(): Boolean {
        val trackers = endpointInteractor.getTrackers().filter {
            // Initially remove content since this causes to block actual websites
            it.category != "Content"
        }
        return if (trackers.isNotEmpty()) {
            runBlocking {
                repository.saveTrackers(trackers)
            }
            Timber.tag("Tracker data update").d("Tracker data downloaded ${trackers.size} trackers")
            true
        } else {
            Timber.tag("Tracker data update").d("Tracker data not data downloaded")
            false
        }
    }
}