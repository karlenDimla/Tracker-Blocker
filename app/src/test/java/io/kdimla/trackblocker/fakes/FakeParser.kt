package io.kdimla.trackblocker.fakes

import io.kdimla.trackblocker.trackerdata.db.model.Tracker
import io.kdimla.trackblocker.trackerdata.source.TrackerDataParser
import io.kdimla.trackblocker.trackerdata.source.disconnect.model.TrackerEntities
import java.util.*

class FakeParser : TrackerDataParser {
    var expectedParsedTrackers = listOf<Tracker>()
    var expectedParsedTrackerEntities = TrackerEntities(listOf(), listOf())
    var stateStack = Stack<State>()
    override fun parseTrackers(rawData: ByteArray): List<Tracker> {
        stateStack.push(State.TrackersParsed(rawData))
        return expectedParsedTrackers
    }

    override fun parseEntities(rawData: ByteArray): TrackerEntities {
        stateStack.push(State.EntitiesParsed(rawData))
        return expectedParsedTrackerEntities
    }

    fun clearStateStack() {
        stateStack.empty()
    }

    sealed class State {
        data class TrackersParsed(val rawData: ByteArray) : State()
        data class EntitiesParsed(val rawData: ByteArray) : State()
    }

}