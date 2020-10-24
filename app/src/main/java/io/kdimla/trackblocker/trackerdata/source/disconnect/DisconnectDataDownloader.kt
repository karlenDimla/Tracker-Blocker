package io.kdimla.trackblocker.trackerdata.source.disconnect

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import io.kdimla.trackblocker.trackerdata.source.TrackerDataDownloader
import io.kdimla.trackblocker.trackerdata.source.endpoint.TrackerDataEndpointInteractor
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@WorkerThread
class DisconnectDataDownloader @Inject constructor(
    private val endpointInteractor: TrackerDataEndpointInteractor,
    private val repository: TrackerDataRepository
) : TrackerDataDownloader {
    override fun downloadData(): Boolean {
        val trackers = endpointInteractor.getTrackers()
        if (trackers.isNotEmpty()) {
            runBlocking {
                repository.saveTrackers(trackers)
            }
        }
        return true
    }
}