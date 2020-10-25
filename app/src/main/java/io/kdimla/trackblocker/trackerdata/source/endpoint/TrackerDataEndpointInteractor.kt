package io.kdimla.trackblocker.trackerdata.source.endpoint

import androidx.annotation.WorkerThread
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import java.io.IOException
import javax.inject.Inject

interface TrackerDataEndpointInteractor {
    fun getTrackers(): List<Tracker>
}

@WorkerThread
class TrackerDataEndpointInteractorImpl @Inject constructor(
    private val endpoint: TrackerDataEndpoint
): TrackerDataEndpointInteractor {
    override fun getTrackers(): List<Tracker> {
        return try {
            endpoint.getTrackerServices().execute().body()?.trackers ?: listOf()
        }catch (e: IOException) {
            listOf()
        }
    }
}