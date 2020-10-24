package io.kdimla.trackblocker.fakes

import io.kdimla.trackblocker.trackerdata.db.dao.TrackerDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerPropertyDao
import io.kdimla.trackblocker.trackerdata.db.dao.TrackerResourceDao
import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.db.model.TrackerProperty
import io.kdimla.trackblocker.trackerdata.db.model.TrackerResource
import java.util.*

class FakeTrackerDao : TrackerDao() {
    var expectedTrackerList = listOf<Tracker>()
    var stateStack = Stack<State>()
    override fun getAll(): List<Tracker> {
        stateStack.push(State.GetAllCalled)
        return expectedTrackerList
    }

    override fun insertAll(trackers: List<Tracker>) {

    }

    override fun deleteAll() {
    }

    override fun getTracker(url: String): List<Tracker> {
        stateStack.push(State.GetTrackerCalled)
        return expectedTrackerList
    }

    fun clearState() {
        stateStack.empty()
    }

    sealed class State {
        object GetAllCalled : State()
        object GetTrackerCalled : State()
    }
}

class FakeTrackerResourceDao : TrackerResourceDao() {
    var shouldUrlBeAResource = false
    var stateStack = Stack<State>()
    var expectedResourceList = listOf<TrackerResource>()
    override fun getAll(): List<TrackerResource> {
        stateStack.push(State.GetAllCalled)
        return expectedResourceList
    }

    override fun insertAll(resources: List<TrackerResource>) {

    }

    override fun deleteAll() {
    }

    override fun countTrackerResource(resourceName: String, companyName: String): Int {
        stateStack.push(State.CountTrackerResourceCalled)
        return if (shouldUrlBeAResource) 1 else 0
    }

    fun clearState() {
        stateStack.empty()
    }

    sealed class State {
        object GetAllCalled : State()
        object CountTrackerResourceCalled : State()
    }
}

class FakeTrackerPropertyDao : TrackerPropertyDao() {
    var expectedPropertyList = listOf<TrackerProperty>()
    var stateStack = Stack<State>()
    override fun getAll(): List<TrackerProperty> {
        stateStack.push(State.GetAllCalled)
        return expectedPropertyList
    }

    override fun insertAll(properties: List<TrackerProperty>) {

    }

    override fun deleteAll() {
    }

    override fun getTrackerProperty(propertyName: String): List<TrackerProperty> {
        stateStack.push(State.GetPropertyCalled)
        return expectedPropertyList
    }

    fun clearState() {
        stateStack.empty()
    }

    sealed class State {
        object GetAllCalled : State()
        object GetPropertyCalled : State()
    }
}