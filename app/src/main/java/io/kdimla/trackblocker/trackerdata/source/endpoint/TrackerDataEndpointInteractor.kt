package io.kdimla.trackblocker.trackerdata.source.endpoint

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import javax.inject.Inject

interface TrackerDataEndpointInteractor {
    fun getTrackers(): List<Tracker>
}

@WorkerThread
class TrackerDataEndpointInteractorImpl @Inject constructor(
    private val endpoint: TrackerDataEndpoint
): TrackerDataEndpointInteractor {
    override fun getTrackers(): List<Tracker> {
        return endpoint.getTrackerServices().execute().body()?.trackers ?: listOf()
    }
}