package io.kdimla.trackblocker.fakes

import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.source.endpoint.TrackerDataEndpointInteractor

class FakeTrackerDataEndpointInteractor: TrackerDataEndpointInteractor {
    var expectedResult = listOf<Tracker>()
    override fun getTrackers(): List<Tracker> = expectedResult
}