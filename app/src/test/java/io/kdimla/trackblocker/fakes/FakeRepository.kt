package io.kdimla.trackblocker.fakes

import io.kdimla.trackblocker.trackerdata.db.TrackerDataRepository
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource
import java.util.*

class FakeRepository : TrackerDataRepository {
    var stateStack = Stack<State>()
    var expectedTrackerList = listOf<Tracker>()
    var expectedPropertyList = listOf<TrackerProperty>()
    override suspend fun saveTrackers(trackers: List<Tracker>) {
        stateStack.push(State.TrackersSaved(trackers))
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

    override fun countTrackers(): Int = expectedTrackerList.size

    override fun countProperties(): Int = expectedPropertyList.size

    override fun getTracker(url: String): List<Tracker> = expectedTrackerList

    fun clearStateStack() {
        stateStack.empty()
    }

    sealed class State {
        data class TrackersSaved(val trackers: List<Tracker>): State()
        data class PropertiesSaved(val props: List<TrackerProperty>): State()
        data class ResourcesSaved(val resources: List<TrackerResource>): State()
    }
}